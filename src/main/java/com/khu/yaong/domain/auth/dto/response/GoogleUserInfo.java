package com.khu.yaong.domain.auth.dto.response;

public class GoogleUserInfo {
    private String name;
    private String email;

    public GoogleUserInfo(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
