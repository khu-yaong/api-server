
package com.khu.yaong.domain.member.domain;

import com.khu.yaong.domain.common.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 20, unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Team team;

    @Builder
    public Member(String username, String password, String email, MemberRole role, Team team, String profileImage) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.team = team;
        this.profileImage = profileImage;
    }

}

