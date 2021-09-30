package com.starsource.allbook.config.auth.filter;

import com.starsource.allbook.UserDetailsImpl;
import com.starsource.allbook.config.auth.dto.SessionUser;
import com.starsource.allbook.member.domain.Member;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final HttpSession httpSession;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        Member member = ((UserDetailsImpl) authentication.getPrincipal()).getMember();
        httpSession.setAttribute("user", new SessionUser(member));
    }
}
