package com.khu.yaong.domain.member.dto.response;

import com.khu.yaong.domain.member.domain.MemberRole;

public class MemberInfoResponseDto {
    private Long id;
    private String username;
    private String email;
    private MemberRole role;
    private String profileImage;

    public MemberInfoResponseDto(Long id, String username, String email, MemberRole role, String profileImage) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.profileImage = profileImage;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public MemberRole getRole() {
        return role;
    }

    public String getProfileImage() {
        return profileImage;
    }
}