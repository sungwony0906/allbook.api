package com.starsource.allbook.member.service;

import com.starsource.allbook.member.domain.Member;
import com.starsource.allbook.member.domain.MemberRepository;
import com.starsource.allbook.member.dto.MemberResponseDto;
import com.starsource.allbook.member.dto.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

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
}
