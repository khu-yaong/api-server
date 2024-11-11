package com.khu.yaong.domain.post.service;

import com.khu.yaong.domain.mapping.domain.MemberPostLike;
import com.khu.yaong.domain.mapping.repository.MemberPostLikeRepository;
import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.member.repository.MemberRepository;
import com.khu.yaong.domain.post.domain.Post;
import com.khu.yaong.domain.post.dto.PostReqDTO;
import com.khu.yaong.domain.post.dto.PostResDTO;
import com.khu.yaong.domain.post.repository.PostRepository;
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

/*---------------------------------------- 게시글 ----------------------------------------*/

    @Override
    public PostResDTO.PostDetailDTO createPost(PostReqDTO.PostDTO postDTO) {

        // Authorization 구현 후 수정 예정
        Member author = memberRepository.findById(1L)
                .orElseThrow(RuntimeException::new);

        Post post = Post.builder()
                .author(author)
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
        Member author = memberRepository.findById(1L)
                .orElseThrow(RuntimeException::new);

        Post post = postRepository.findById(postId)
                .orElseThrow(RuntimeException::new);

        post.updatePost(postDTO.getTitle(), postDTO.getContent(), postDTO.getImageUrl());
        Post updatedPost = postRepository.save(post);
        author.updatePost(updatedPost);

        return PostResDTO.PostDetailDTO.toDTO(updatedPost);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResDTO.PostDetailDTO getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(RuntimeException::new);
        return PostResDTO.PostDetailDTO.toDTO(post);
    }


/*---------------------------------------- 좋아요 ----------------------------------------*/

    @Override
    public PostResDTO.PostStatusDTO likePost(Long postId) {

        // Authorization 구현 후 수정 예정
        Member member = memberRepository.findById(1L)
                .orElseThrow(RuntimeException::new);

        Post post = postRepository.findById(postId)
                .orElseThrow(RuntimeException::new);

        // 중복해서 좋아요를 누를 수 없음
        if (memberPostLikeRepository.existsByMemberIdAndPostId(member.getId(), postId)) {
            throw new RuntimeException("중복 좋아요 불가");
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
                .orElseThrow(RuntimeException::new);

        Post post = postRepository.findById(postId)
                .orElseThrow(RuntimeException::new);

        MemberPostLike memberPostLike = memberPostLikeRepository.findByMemberIdAndPostId(member.getId(), postId)
                .orElseThrow(RuntimeException::new);

        memberPostLikeRepository.delete(memberPostLike);
        post.deleteMemberPostLike(memberPostLike);
        post.minusCountLike();
        Post updatedPost = postRepository.save(post);

        return PostResDTO.PostStatusDTO.toDTO(updatedPost);
    }

    /*---------------------------------------- 댓글 ----------------------------------------*/
}
