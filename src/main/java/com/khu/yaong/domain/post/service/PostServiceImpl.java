package com.khu.yaong.domain.post.service;

import com.khu.yaong.domain.comment.repository.CommentRepository;
import com.khu.yaong.domain.mapping.domain.MemberPostLike;
import com.khu.yaong.domain.mapping.repository.MemberPostLikeRepository;
import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.mapping.domain.MemberPostLike;
import com.khu.yaong.domain.mapping.repository.MemberPostLikeRepository;
import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.member.repository.MemberRepository;
import com.khu.yaong.domain.post.domain.Post;
import com.khu.yaong.domain.post.dto.PostReqDTO;
import com.khu.yaong.domain.post.dto.PostResDTO;
import com.khu.yaong.domain.post.repository.PostRepository;
import com.khu.yaong.global.common.exception.BaseException;
import com.khu.yaong.global.common.response.member.MemberErrorCode;
import com.khu.yaong.global.common.response.post.PostErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final MemberPostLikeRepository memberPostLikeRepository;
    private final CommentRepository commentRepository;

/*---------------------------------------- 게시글 ----------------------------------------*/

    @Override
    public PostResDTO.PostDetailDTO createPost(PostReqDTO.PostDTO postDTO) {

        // Authorization 구현 후 수정 예정
        Member author = memberRepository.findById(1L)
                .orElseThrow(() -> new BaseException(MemberErrorCode.MEMBER_NOT_FOUND));

        Post post = Post.builder()
                .author(author)
                .category(postDTO.getCategory())
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .imageUrl(postDTO.getImageUrl())
                .countLike(0L)
                .countComment(0L)
                .build();

        Post savedPost = postRepository.save(post);
        author.addPost(savedPost);

        return PostResDTO.PostDetailDTO.toDTO(savedPost);
    }

    @Override
    public PostResDTO.PostDetailDTO updatePost(Long postId, PostReqDTO.PostDTO postDTO) {

        // Authorization 구현 후 수정 예정
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new BaseException(MemberErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(PostErrorCode.POST_NOT_FOUND));

        // 게시글 작성자와 로그인 회원 일치 여부 확인
        if (!member.equals(post.getAuthor())) {
            throw new BaseException(PostErrorCode.POST_AUTHOR_ONLY_ALLOWED);
        }

        post.updatePost(postDTO.getCategory(), postDTO.getTitle(), postDTO.getContent(), postDTO.getImageUrl());
        Post updatedPost = postRepository.save(post);
        member.updatePost(updatedPost);

        return PostResDTO.PostDetailDTO.toDTO(updatedPost);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResDTO.PostDetailDTO getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(RuntimeException::new);
        return PostResDTO.PostDetailDTO.toDTO(post);
    }

    @Override
    public PostResDTO.PostStatusDTO deletePost(Long postId) {

        // Authorization 구현 후 수정 예정
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new BaseException(MemberErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(PostErrorCode.POST_NOT_FOUND));

        // 게시글 작성자와 로그인 회원 일치 여부 확인
        if (!member.equals(post.getAuthor())) {
            throw new BaseException(PostErrorCode.POST_AUTHOR_ONLY_ALLOWED);
        }

        // 게시글 댓글 삭제
        commentRepository.deleteAllByPostId(postId);
        postRepository.delete(post);
        member.deletePost(post);

        return PostResDTO.PostStatusDTO.toDTO(post);
    }


    /*---------------------------------------- 좋아요 ----------------------------------------*/

    @Override
    public PostResDTO.PostStatusDTO likePost(Long postId) {

        // Authorization 구현 후 수정 예정
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new BaseException(MemberErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(PostErrorCode.POST_NOT_FOUND));

        // 중복해서 좋아요를 누를 수 없음
        if (memberPostLikeRepository.existsByMemberIdAndPostId(member.getId(), postId)) {
            throw new BaseException(PostErrorCode.DUPLICATE_POST_LIKES_NOT_ALLOWED);
        }

        MemberPostLike memberPostLike = MemberPostLike.builder()
                .member(member)
                .post(post)
                .build();

        MemberPostLike savedMemberPostLike = memberPostLikeRepository.save(memberPostLike);
        post.addMemberPostLike(savedMemberPostLike);
        post.plusCountLike();
        Post updatedPost = postRepository.save(post);

        return PostResDTO.PostStatusDTO.toDTO(updatedPost);
    }

    @Override
    public PostResDTO.PostStatusDTO cancelLikePost(Long postId) {

        // Authorization 구현 후 수정 예정
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new BaseException(MemberErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(PostErrorCode.POST_NOT_FOUND));

        MemberPostLike memberPostLike = memberPostLikeRepository.findByMemberIdAndPostId(member.getId(), postId)
                .orElseThrow(RuntimeException::new);

        memberPostLikeRepository.delete(memberPostLike);
        post.deleteMemberPostLike(memberPostLike);
        post.minusCountLike();
        Post updatedPost = postRepository.save(post);

        return PostResDTO.PostStatusDTO.toDTO(updatedPost);
    }
}
