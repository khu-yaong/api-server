package com.khu.yaong.domain.comment.service;

import com.khu.yaong.domain.comment.domain.Comment;
import com.khu.yaong.domain.comment.dto.CommentReqDTO;
import com.khu.yaong.domain.comment.dto.CommentResDTO;
import com.khu.yaong.domain.comment.repository.CommentRepository;
import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.member.repository.MemberRepository;
import com.khu.yaong.domain.post.domain.Post;
import com.khu.yaong.domain.post.repository.PostRepository;
import com.khu.yaong.global.common.exception.BaseException;
import com.khu.yaong.global.common.response.comment.CommentErrorCode;
import com.khu.yaong.global.common.response.member.MemberErrorCode;
import com.khu.yaong.global.common.response.post.PostErrorCode;
import com.khu.yaong.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

/*---------------------------------------- 댓글 ----------------------------------------*/

    @Override
    public CommentResDTO.CommentDetailDTO createComment(Long postId, CommentReqDTO commentReqDTO) {

        // Authorization
        Long memberId = SecurityUtil.getCurrentMemberId();
        Member author = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(MemberErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(PostErrorCode.POST_NOT_FOUND));

        Comment comment = Comment.builder()
                .author(author)
                .post(post)
                .content(commentReqDTO.getContent())
                .build();

        Comment savedComment = commentRepository.save(comment);
        post.addComment(savedComment);

        return CommentResDTO.CommentDetailDTO.toDTO(savedComment);
    }

    @Override
    public CommentResDTO.CommentDetailDTO deleteComment(Long commentId) {

        // Authorization
        Long memberId = SecurityUtil.getCurrentMemberId();
        Member author = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(MemberErrorCode.MEMBER_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(CommentErrorCode.COMMENT_NOT_FOUND));

        // 댓글 작성자만 삭제 가능
        if (!comment.getAuthor().getId().equals(author.getId())) {
            throw new RuntimeException();
        }

        commentRepository.delete(comment);
        comment.getPost().deleteComment(comment);

        return CommentResDTO.CommentDetailDTO.toDTO(comment);
    }
}
