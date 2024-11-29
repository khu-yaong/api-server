package com.khu.yaong.domain.post.dto;

import com.khu.yaong.domain.comment.dto.CommentResDTO;
import com.khu.yaong.domain.member.domain.Team;
import com.khu.yaong.domain.post.domain.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResDTO {

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class PostInfoDTO {

        // author info
        private final String authorName;
        private final String authorProfileImage;
        private final Team authorTeam;

        // post info
        private final Long postId;
        private final String title;
        private final String content;
        private final String imageUrl;
        private final LocalDateTime createdDate;
        private final Long countLike;
        private final Long countComment;

        public static PostInfoDTO toDTO(Post post) {
            return PostInfoDTO.builder()
                    .authorName(post.getAuthor().getUsername())
                    .authorProfileImage(post.getAuthor().getProfileImage())
                    .authorTeam(post.getAuthor().getTeam())
                    .postId(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .imageUrl(post.getImageUrl())
                    .createdDate(post.getCreatedDate())
                    .countLike(post.getCountLike())
                    .countComment(post.getCountComment())
                    .build();
        }
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class PostDetailDTO {

        private final PostInfoDTO post;
        private final List<CommentResDTO.CommentDetailDTO> comments;

        public static PostDetailDTO toDTO(Post post) {
            return PostDetailDTO.builder()
                    .post(PostInfoDTO.toDTO(post))
                    .comments(post.getComments().stream()
                            .map(CommentResDTO.CommentDetailDTO::toDTO)
                            .toList())
                    .build();
        }
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class PostStatusDTO {

        private final Long postId;
        private final Long likeCount;
        private final Long countComment;
        private final Boolean isLiked;

        public static PostStatusDTO toDTO(Post post, Boolean isLiked) {
            return PostStatusDTO.builder()
                    .postId(post.getId())
                    .likeCount(post.getCountLike())
                    .countComment(post.getCountComment())
                    .isLiked(isLiked)
                    .build();
        }
    }
}
