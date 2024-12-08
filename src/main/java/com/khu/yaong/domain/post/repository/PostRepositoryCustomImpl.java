package com.khu.yaong.domain.post.repository;

import com.khu.yaong.domain.member.domain.QMember;
import com.khu.yaong.domain.member.domain.Team;
import com.khu.yaong.domain.post.domain.Category;
import com.khu.yaong.domain.post.domain.Post;
import com.khu.yaong.domain.post.domain.QPost;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Post> findPostsByCategoryAndTeam(Category category, Team team, LocalDateTime cursorDateTime, Long cursorPostId, Integer pageSize) {
        QPost post = QPost.post;
        QMember member = QMember.member;

        BooleanBuilder whereClause = getWhereClause(category, team, cursorDateTime, cursorPostId, post);

        return queryFactory.selectFrom(post)
                .join(member).on(member.id.eq(post.author.id))
                .where(whereClause)
                .orderBy(post.createdDate.desc(), post.id.asc())
                .limit(pageSize)
                .fetch();
    }

    private static BooleanBuilder getWhereClause(
            Category category, Team team, LocalDateTime cursorDateTime, Long cursorPostId, QPost post
    ) {
        BooleanBuilder whereClause = new BooleanBuilder();
        whereClause.and(post.category.eq(category));

        if (team != null) {
            whereClause.and(post.author.team.eq(team));
        }

        if (cursorDateTime != null && cursorPostId != null) {
            whereClause.and(post.createdDate.lt(cursorDateTime)
                    .or(post.createdDate.eq(cursorDateTime).and(post.id.lt(cursorPostId))));
        }

        return whereClause;
    }
}
