package com.khu.yaong.domain.comment.controller;

import com.khu.yaong.domain.comment.dto.CommentReqDTO;
import com.khu.yaong.domain.comment.dto.CommentResDTO;
import com.khu.yaong.domain.comment.service.CommentService;
import com.khu.yaong.global.common.response.ApiResponse;
import com.khu.yaong.global.common.response.comment.CommentSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Home API", description = "홈화면(게시글) 관련 API")
public class CommentController {

    private final CommentService commentService;

/*---------------------------------------- 댓글 ----------------------------------------*/

    @Operation(summary = "[구현완료] 게시글 댓글 작성하기", description = """
    ## 게시글에 댓글을 작성합니다. 댓글 정보는 DB에 저장됩니다.
    * input : postId, 댓글 내용
    * output : 작성된 댓글의 상세 정보
    """)
    @PostMapping("/posts/{postId}/comments")
    ApiResponse<CommentResDTO.CommentDetailDTO> createComment(
            @PathVariable Long postId, @RequestBody CommentReqDTO commentReqDTO) {
        CommentResDTO.CommentDetailDTO commentDetailDTO = commentService.createComment(postId, commentReqDTO);
        return ApiResponse.success(CommentSuccessCode.COMMENT_CREATED, commentDetailDTO);
    }

    @Operation(summary = "[구현완료] 게시글 댓글 삭제하기", description = """
    ## 게시글 댓글 정보가 DB에서 삭제됩니다.
    * input : commentId
    * output : 삭제된 댓글의 상세 정보
    """)
    @DeleteMapping("/comments/{commentId}")
    ApiResponse<CommentResDTO.CommentDetailDTO> deleteComment(@PathVariable Long commentId) {
        CommentResDTO.CommentDetailDTO commentDetailDTO = commentService.deleteComment(commentId);
        return ApiResponse.success(CommentSuccessCode.COMMENT_DELETED, commentDetailDTO);
    }
}
