package com.mm.coredomain.domain;

public enum OAuthProvider {
    KAKAO, APPLE;

    public static OAuthProvider of(String input) {
        try {
            return OAuthProvider.valueOf(input.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("존재하지 소셜 로그인 타입입니다. : " + input);
        }
    }
}
