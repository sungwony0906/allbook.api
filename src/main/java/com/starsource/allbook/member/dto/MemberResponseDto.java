package com.starsource.allbook.member.dto;

import com.starsource.allbook.member.domain.Address;
import com.starsource.allbook.member.domain.Member;
import com.starsource.allbook.member.domain.MemberStatus;
import com.starsource.allbook.member.domain.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberResponseDto {

    private Long id;
    private Role role;
    private MemberStatus memberStatus;
    private String name;
    private String email;
    private String picture;
    private Address address;

    @Builder
    public MemberResponseDto(Long id, Role role, MemberStatus memberStatus, String name, String email, String picture, Address address){
        this.id = id;
        this.role = role;
        this.memberStatus = memberStatus;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.address = address;
    }

    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                       .id(member.getId())
                       .role(member.getRole())
                       .memberStatus(member.getMemberStatus())
                       .name(member.getName())
                       .email(member.getEmail())
                       .picture(member.getPicture())
                       .address(member.getAddress())
                       .build();
    }
}
