package com.khu.yaong.domain.post.controller;

import com.khu.yaong.domain.member.domain.Team;
import com.khu.yaong.domain.post.domain.Category;
import com.khu.yaong.domain.post.dto.PostReqDTO;
import com.khu.yaong.domain.post.dto.PostResDTO;
import com.khu.yaong.domain.post.service.PostService;
import com.khu.yaong.global.common.response.ApiResponse;
import com.khu.yaong.global.common.response.post.PostSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Home API", description = "홈화면(게시글) 관련 API")
public class PostController {

    private final PostService postService;

/*---------------------------------------- 게시글 ----------------------------------------*/

    @Operation(summary = "[구현완료] 게시글 작성하기", description = """
    ## 게시글을 생성하여 DB에 저장합니다.
    * input : 게시글 내용 (카테고리, 제목, 본문, 사진)
    * output : 작성된 게시글의 상세 정보
    * category : VIEW,
                 REVIEW,
                 DISCUSSION,
                 TALK,
                 TIP,
                 ETC
    """)
    @PostMapping("/posts")
    ApiResponse<PostResDTO.PostDetailDTO> createPost(@RequestBody PostReqDTO.PostDTO postDTO) {
        PostResDTO.PostDetailDTO postDetailDTO = postService.createPost(postDTO);
        return ApiResponse.success(PostSuccessCode.POST_CREATED, postDetailDTO);
    }

    @Operation(summary = "[구현완료] 게시글 수정하기", description = """
    ## 기존의 게시글을 수정하여 DB에 저장합니다.
    * input : postId, 게시글 내용 (제목, 본문, 사진)
    * output : 수정된 게시글의 상세 정보
    * category : VIEW,
                 REVIEW,
                 DISCUSSION,
                 TALK,
                 TIP,
                 ETC
    """)
    @PatchMapping("/posts/{postId}")
    ApiResponse<PostResDTO.PostDetailDTO> updatePost(@PathVariable Long postId, @RequestBody PostReqDTO.PostDTO postDTO) {
        PostResDTO.PostDetailDTO postDetailDTO = postService.updatePost(postId, postDTO);
        return ApiResponse.success(PostSuccessCode.POST_UPDATED, postDetailDTO);
    }

    @Operation(summary = "[구현완료] 게시글 조회하기", description = """
    ## 게시글의 상세 정보(제목, 본문, 사진, 좋아요 수, 댓글 수, 댓글 목록)를 조회합니다.
    * input : postId
    * output : 게시글의 상세 정보
    """)
    @GetMapping("/posts/{postId}")
    ApiResponse<PostResDTO.PostDetailDTO> getPost(@PathVariable Long postId) {
        PostResDTO.PostDetailDTO postDetailDTO = postService.getPost(postId);
        return ApiResponse.success(PostSuccessCode.POST_FOUND, postDetailDTO);
    }

    @Operation(summary = "[구현완료] 게시글 삭제하기", description = """
    ## 작성한 게시글을 삭제합니다.
    * input : postId
    * output : 삭제된 게시글의 요약 정보 (id, 좋아요 수, 댓글 수)
    """)
    @DeleteMapping("/posts/{postId}")
    ApiResponse<PostResDTO.PostStatusDTO> deletePost(@PathVariable Long postId) {
        PostResDTO.PostStatusDTO postStatusDTO = postService.deletePost(postId);
        return ApiResponse.success(PostSuccessCode.POST_DELETED, postStatusDTO);
    }

    @Operation(summary = "[구현완료] 게시글 목록 조회하기", description = """
    ## 게시글 목록을 조회합니다.
    * team(nullable) : team이 null이면 모든 구단의 게시글을, team이 null이 아니면 특정 구단의 게시글을 필터링합니다.
    * category : 특정 카테고리의 게시글을 필터링합니다.
    * size : 페이지 사이즈 (1 이상)
    * cursorDateTime(nullable) : 이전 페이지의 가장 마지막 게시글 생성 시각 (이전 페이지가 없다면 null)
    * cursorPostId(nullable) : 이전 페이지의 가장 마지막 게시글 아이디 (이전 페이지가 없다면 null)
    output : 필터링 된 게시글 목록
    """)
    @GetMapping("/posts")
    ApiResponse<List<PostResDTO.PostInfoDTO>> getPosts(
            @RequestParam Category category,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) Team team,
            @RequestParam(required = false) LocalDateTime cursorDateTime,
            @RequestParam(required = false) Long cursorPostId
            ) {
        List<PostResDTO.PostInfoDTO> postInfoDTOS = postService.getPosts(category, team, cursorDateTime, cursorPostId, pageSize);
        return ApiResponse.success(PostSuccessCode.POST_FOUND, postInfoDTOS);
    }
/*---------------------------------------- 좋아요 ----------------------------------------*/

    @Operation(summary = "[구현완료] 게시글 좋아요 누르기", description = """
    ## 게시글에 좋아요를 누릅니다.
    * 회원의 게시글 좋아요 내역이 DB에 저장되고, 게시글의 좋아요 수가 1 증가합니다.
    * input : postId
    * output : 게시글의 상태 정보(id, 좋아요 수, 댓글 수)
    """)
    @PostMapping("/posts/{postId}/likes")
    ApiResponse<PostResDTO.PostStatusDTO> likePost(@PathVariable Long postId) {
        PostResDTO.PostStatusDTO postStatusDTO = postService.likePost(postId);
        return ApiResponse.success(PostSuccessCode.POST_LIKED, postStatusDTO);
    }

    @Operation(summary = "[구현완료] 게시글 좋아요 취소하기", description = """
    ## 게시글 좋아요를 취소합니다.
    * 회원의 게시글 좋아요 내역이 DB에서 삭제되고, 게시글의 좋아요 수가 1 감소합니다.
    * input : postId
    * output : 게시글의 상태 정보(id, 좋아요 수, 댓글 수)
    """)
    @DeleteMapping("/posts/{postId}/likes")
    ApiResponse<PostResDTO.PostStatusDTO> cancelLikePost(@PathVariable Long postId) {
        PostResDTO.PostStatusDTO postStatusDTO = postService.cancelLikePost(postId);
        return ApiResponse.success(PostSuccessCode.POST_LIKE_CANCELED, postStatusDTO);
    }
}
