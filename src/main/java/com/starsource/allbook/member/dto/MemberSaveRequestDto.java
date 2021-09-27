package com.starsource.allbook.member.dto;

import com.starsource.allbook.member.domain.Address;
import com.starsource.allbook.member.domain.Member;
import com.starsource.allbook.member.domain.MemberStatus;
import com.starsource.allbook.member.domain.Role;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class MemberSaveRequestDto {

    @NotNull
    private Role role;

    private MemberStatus memberStatus;

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String email;

    private String picture;

    private Address address;

    @NotEmpty
    private String password;

    public Member toEntity() {
        return Member.builder()
                       .role(role)
                       .memberStatus(memberStatus)
                       .name(name)
                       .email(email)
                       .picture(picture)
                       .address(address)
                       .password(password)
                       .build();
    }
}
