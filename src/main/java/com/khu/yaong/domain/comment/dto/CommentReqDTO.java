package com.khu.yaong.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.Getter;

@Getter
public class CommentReqDTO {
    private final String content;
    public CommentReqDTO(@JsonProperty("content") String content) {
        this.content = content;
    }
}
