package com.starsource.allbook.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    MEMBER("ROLE_MEMBER", "일반회원"),
    CORPORATOR("ROLE_CORPORATOR", "법인회원"),
    ADMIN("ROLE_ADMIN", "관리자"),
    OPREATOR("ROLE_OPERATOR", "운영자");

    private final String key;
    private final String title;
}
