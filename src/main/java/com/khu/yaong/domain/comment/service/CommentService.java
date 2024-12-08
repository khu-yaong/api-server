package com.khu.yaong.domain.comment.service;

import com.khu.yaong.domain.comment.dto.CommentReqDTO;
import com.khu.yaong.domain.comment.dto.CommentResDTO;

public interface CommentService {

/*---------------------------------------- 댓글 ----------------------------------------*/

    CommentResDTO.CommentInfoDTO createComment(Long postId, CommentReqDTO commentReqDTO);

    CommentResDTO.CommentInfoDTO deleteComment(Long commentId);
}
