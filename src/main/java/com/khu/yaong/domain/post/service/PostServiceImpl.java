package com.khu.yaong.domain.post.service;

import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.member.repository.MemberRepository;
import com.khu.yaong.domain.post.domain.Post;
import com.khu.yaong.domain.post.dto.PostReqDTO;
import com.khu.yaong.domain.post.dto.PostResDTO;
import com.khu.yaong.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

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

/*---------------------------------------- 좋아요 ----------------------------------------*/

/*---------------------------------------- 댓글 ----------------------------------------*/
}
