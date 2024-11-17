package com.khu.yaong.domain.post.service;

import com.khu.yaong.domain.member.domain.Team;
import com.khu.yaong.domain.post.domain.Category;
import com.khu.yaong.domain.post.dto.PostReqDTO;
import com.khu.yaong.domain.post.dto.PostResDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface PostService {

/*---------------------------------------- 게시글 ----------------------------------------*/

    PostResDTO.PostDetailDTO createPost(PostReqDTO.PostDTO postDTO);

    PostResDTO.PostDetailDTO updatePost(Long postId, PostReqDTO.PostDTO postDTO);

    PostResDTO.PostDetailDTO getPost(Long postId);

    PostResDTO.PostStatusDTO deletePost(Long postId);

    List<PostResDTO.PostInfoDTO> getPosts(Category category, Team team, LocalDateTime cursorDateTime, Long cursorPostId, Integer pageSize);
/*---------------------------------------- 좋아요 ----------------------------------------*/

    PostResDTO.PostStatusDTO likePost(Long postId);

    PostResDTO.PostStatusDTO cancelLikePost(Long postId);
}
