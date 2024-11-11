package com.khu.yaong.domain.post.service;

import com.khu.yaong.domain.post.dto.PostReqDTO;
import com.khu.yaong.domain.post.dto.PostResDTO;

public interface PostService {

/*---------------------------------------- 게시글 ----------------------------------------*/

    PostResDTO.PostDetailDTO createPost(PostReqDTO.PostDTO postDTO);

    PostResDTO.PostDetailDTO updatePost(Long postId, PostReqDTO.PostDTO postDTO);

/*---------------------------------------- 좋아요 ----------------------------------------*/

/*---------------------------------------- 댓글 ----------------------------------------*/
}
