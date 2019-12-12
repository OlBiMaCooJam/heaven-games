package com.olbimacoojam.heaven.service;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TokenInfo {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private String expires_in;
    private String scope;
    private String refresh_token_expires_in;

    public TokenInfo() {
    }

    public TokenInfo(String access_token, String token_type, String refresh_token, String expires_in, String scope, String refresh_token_expires_in) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.scope = scope;
        this.refresh_token_expires_in = refresh_token_expires_in;
    }
}
