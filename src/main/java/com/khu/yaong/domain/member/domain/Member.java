
package com.khu.yaong.domain.member.domain;

import com.khu.yaong.domain.common.BaseTime;
import com.khu.yaong.domain.diary.domain.Diary;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "member_roles", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "role")
    private Set<String> roles; // 사용자 역할 목록 (예: ROLE_USER, ROLE_ADMIN)
    // 권한 목록을 반환하는 메서드
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diary> diariyList;
}

