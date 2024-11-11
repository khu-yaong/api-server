package com.khu.yaong.domain.post.service;

import com.khu.yaong.domain.member.repository.MemberRepository;
import com.khu.yaong.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

/*---------------------------------------- 게시글 ----------------------------------------*/

/*---------------------------------------- 좋아요 ----------------------------------------*/

/*---------------------------------------- 댓글 ----------------------------------------*/
}
