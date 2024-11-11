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
    public static class PostDetailDTO {

        // author info
        private final String authorName;
        private final String authorProfileImage;
        private final Team authorTeam;

        // post info
        private final String title;
        private final String content;
        private final String imageUrl;
        private final LocalDateTime createdDate;
        private final Long countLike;
        private final Long countComment;

        // comment info
        private final List<CommentResDTO.CommentDetailDTO> comments;

        public static PostDetailDTO toDTO(Post post) {
            return PostDetailDTO.builder()
                    .authorName(post.getAuthor().getUsername())
                    .authorProfileImage(post.getAuthor().getProfileImage())
                    .authorTeam(post.getAuthor().getTeam())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .imageUrl(post.getImageUrl())
                    .createdDate(post.getCreatedDate())
                    .countLike(post.getCountLike())
                    .countComment(post.getCountComment())
                    .comments(post.getComments().stream()
                            .map(CommentResDTO.CommentDetailDTO::toDTO)
                            .toList())
                    .build();
        }
    }
}
