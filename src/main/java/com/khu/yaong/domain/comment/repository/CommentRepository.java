package com.khu.yaong.domain.comment.repository;

import com.khu.yaong.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    void deleteAllByPostId(Long postId);
}
