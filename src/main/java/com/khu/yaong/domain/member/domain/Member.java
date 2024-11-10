
package com.khu.yaong.domain.member.domain;

import com.khu.yaong.domain.common.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Team team;


}

