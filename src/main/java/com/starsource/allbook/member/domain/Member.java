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
import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.Audited;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Audited
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
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

    public Member updateBasicInfo(String name, String email, String picture) {
        if(StringUtils.isNotBlank(name))
            this.name = name;
        if(StringUtils.isNotBlank(email))
            this.email = email;
        if(StringUtils.isNotBlank(picture))
            this.picture = picture;
        return this;
    }

    public Member updateAddress(Address address){
        if(address != null)
            this.address = address;
        return this;
    }

    public Member withdraw() {
        if(!memberStatus.equals(MemberStatus.WITHDRAW)){
            this.memberStatus = MemberStatus.WITHDRAW;
        }
        return this;
    }
}
