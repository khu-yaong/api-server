
package com.khu.yaong.domain.member.domain;

import com.khu.yaong.domain.common.BaseTime;
import com.khu.yaong.domain.mapping.domain.MemberPostLike;
import com.khu.yaong.domain.post.domain.Post;
import com.khu.yaong.domain.diary.domain.Diary;
import com.khu.yaong.domain.mapping.domain.MemberPostLike;
import com.khu.yaong.domain.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.NoSuchElementException;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MemberLevel memberLevel;

    @Builder.Default
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberPostLike> memberPostLikes = new ArrayList<>();

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

    public void addPost(Post post) {
        posts.add(post);
        post.setAuthor(this);
    }

    public void updatePost(Post updatedPost) {
        Post existingPost = posts.stream()
                .filter(post -> post.getId().equals(updatedPost.getId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 게시물을 찾을 수 없습니다."));
        existingPost.updatePost(updatedPost.getTitle(), updatedPost.getContent(), updatedPost.getImageUrl());
        updatedPost.setAuthor(this);
    }

    public void addMemberPostLike(MemberPostLike memberPostLike) {
        memberPostLikes.add(memberPostLike);
        memberPostLike.setMember(this);
    }
}

