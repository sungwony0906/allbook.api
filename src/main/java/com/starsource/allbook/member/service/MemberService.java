package com.starsource.allbook.member.service;

import com.starsource.allbook.config.auth.dto.SessionUser;
import com.starsource.allbook.member.domain.Member;
import com.starsource.allbook.member.domain.MemberRepository;
import com.starsource.allbook.member.dto.MemberResponseDto;
import com.starsource.allbook.member.dto.MemberSaveRequestDto;
import com.starsource.allbook.member.dto.MemberUpdateRequestDto;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    public MemberResponseDto saveMember(MemberSaveRequestDto requestDto) {
        memberRepository.findByEmail(requestDto.getEmail()).ifPresent(e -> {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        });
        return MemberResponseDto.of(memberRepository.save(requestDto.toEntity()));
    }

    public MemberResponseDto deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                                .orElseThrow();
        return MemberResponseDto.of(member.withdraw());
    }

    public MemberResponseDto updateMember(Long id, MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findById(id)
                                .orElseThrow();
        member.updateBasicInfo(requestDto.getName(), requestDto.getEmail(), requestDto.getPicture());
        member.updateAddress(requestDto.getAddress());
        return MemberResponseDto.of(member);
    }

    public MemberResponseDto updatePassword(Long id, String oldPassword, String password) {
        Member member = memberRepository.findById(id)
                .orElseThrow();
        return MemberResponseDto.of(member.updatePassword(oldPassword, password));
    }

    public MemberResponseDto findMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow();
        return MemberResponseDto.of(member);
    }

    public MemberResponseDto findMemberByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow();
        return MemberResponseDto.of(member);
    }

    public void login(String email, String password) throws Exception {
        Member member = memberRepository.findByEmail(email)
                                .orElseThrow();
        if(!member.getPassword().equals(password)) {
            throw new Exception();
        }
        httpSession.setAttribute("user", new SessionUser(member));
    }
}
