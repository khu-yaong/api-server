package com.khu.yaong.domain.post.repository;

import com.khu.yaong.domain.member.domain.Team;
import com.khu.yaong.domain.post.domain.Category;
import com.khu.yaong.domain.post.domain.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepositoryCustom {

    List<Post> findPostsByCategoryAndTeam(Category category, Team team, LocalDateTime cursorDateTime, Long cursorPostId, Integer pageSize);
}
