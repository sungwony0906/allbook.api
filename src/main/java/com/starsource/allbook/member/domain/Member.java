package com.starsource.allbook.member.domain;

import com.starsource.allbook.common.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus memberStatus;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String picture;

    @Embedded
    private Address address;

    @Builder
    public Member(String name, String email, Role role, Address address, String picture, MemberStatus memberStatus){
        this.name = name;
        this.email = email;
        this.role = role;
        this.address = address;
        this.picture = picture;
        this.memberStatus = memberStatus;
    }

    public Member update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }
}
