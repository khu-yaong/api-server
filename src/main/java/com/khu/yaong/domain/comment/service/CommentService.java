package com.khu.yaong.domain.comment.service;

import com.khu.yaong.domain.comment.dto.CommentReqDTO;
import com.khu.yaong.domain.comment.dto.CommentResDTO;

public interface CommentService {

/*---------------------------------------- 댓글 ----------------------------------------*/

    CommentResDTO.CommentDetailDTO createComment(Long postId, CommentReqDTO commentReqDTO);

    CommentResDTO.CommentDetailDTO deleteComment(Long commentId);
}
