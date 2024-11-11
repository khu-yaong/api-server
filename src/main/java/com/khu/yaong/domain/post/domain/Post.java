package com.khu.yaong.domain.post.domain;

import com.khu.yaong.domain.comment.domain.Comment;
import com.khu.yaong.domain.common.BaseTime;
import com.khu.yaong.domain.mapping.domain.MemberPostLike;
import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.post.dto.PostReqDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id; // 게시글 ID

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member author; // 작성자

    @Column(nullable = false, length = 100)
    private String title;

    private String content;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "count_like", nullable = false)
    private Long countLike = 0L;

    @Column(name = "count_comment", nullable = false)
    private Long countComment = 0L;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberPostLike> memberPostLikes = new ArrayList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void deleteComment(Comment comment) {
        comments.remove(comment);
    }

    public void addMemberPostLike(MemberPostLike memberPostLike) {
        memberPostLikes.add(memberPostLike);
        memberPostLike.setPost(this);
    }

    public void deleteMemberPostLike(MemberPostLike memberPostLike) {
        memberPostLikes.remove(memberPostLike);
    }

    public void plusCountLike() {
        this.countLike++;
    }

    public void minusCountLike() {
        this.countLike--;
    }

    public void updatePost(String title, String content, String imageUrl) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }
}
