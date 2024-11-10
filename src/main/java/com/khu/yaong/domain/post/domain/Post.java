package com.khu.yaong.domain.post.domain;

import com.khu.yaong.domain.comment.domain.Comment;
import com.khu.yaong.domain.common.BaseTime;
import com.khu.yaong.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id; // 게시글 ID

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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
}
