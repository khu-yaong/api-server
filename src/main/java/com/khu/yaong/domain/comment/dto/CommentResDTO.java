package com.khu.yaong.domain.comment.dto;

import com.khu.yaong.domain.comment.domain.Comment;
import com.khu.yaong.domain.member.domain.Team;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class CommentResDTO {

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class CommentInfoDTO {

        private final Long commentCount;
        private final CommentDetailDTO comment;

        public static CommentInfoDTO toDTO(Comment comment) {
            return CommentInfoDTO.builder()
                    .commentCount(comment.getPost().getCountComment())
                    .comment(CommentDetailDTO.toDTO(comment))
                    .build();
        }
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class CommentDetailDTO {

        // author info
        private final String authorName;
        private final String authorProfileImage;
        private final Team authorTeam;

        // comment info
        private final Long commentId;
        private final String content;
        private final LocalDateTime createdDate;

        public static CommentDetailDTO toDTO(Comment comment) {
            return CommentDetailDTO.builder()
                    .authorName(comment.getAuthor().getUsername())
                    .authorProfileImage(comment.getAuthor().getProfileImage())
                    .authorTeam(comment.getAuthor().getTeam())
                    .commentId(comment.getId())
                    .content(comment.getContent())
                    .createdDate(comment.getCreatedDate())
                    .build();
        }
    }
}
