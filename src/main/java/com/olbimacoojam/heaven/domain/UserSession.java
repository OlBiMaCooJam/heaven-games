package com.olbimacoojam.heaven.domain;

import lombok.Getter;

@Getter
public class UserSession {
    public static final String USER_SESSION = "user";

    private Long id;
    private String name;
    private String accessToken;

    public UserSession(Long id, String name, String accessToken) {
        this.id = id;
        this.name = name;
        this.accessToken = accessToken;
    }
}
