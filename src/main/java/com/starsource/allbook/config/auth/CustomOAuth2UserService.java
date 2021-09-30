package com.starsource.allbook.config.auth;

import com.starsource.allbook.UserDetailsImpl;
import com.starsource.allbook.config.auth.dto.OAuthAttributes;
import com.starsource.allbook.config.auth.dto.SessionUser;
import com.starsource.allbook.member.domain.Member;
import com.starsource.allbook.member.domain.MemberRepository;
import java.util.Collections;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>, UserDetailsService {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                                               .getProviderDetails()
                                               .getUserInfoEndpoint()
                                               .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        Member member = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(member));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    private Member saveOrUpdate(OAuthAttributes attributes) {
        Member member = memberRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.updateBasicInfo(attributes.getName(), attributes.getEmail(), attributes.getPicture()))
                .orElse(attributes.toEntity());
        return memberRepository.save(member);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                                .orElseThrow();
        return new UserDetailsImpl(member);
    }
}
