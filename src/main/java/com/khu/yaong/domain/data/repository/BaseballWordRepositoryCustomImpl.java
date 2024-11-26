package com.khu.yaong.domain.data.repository;

import com.khu.yaong.domain.data.domain.BaseballWord;
import com.khu.yaong.domain.data.domain.QBaseballWord;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BaseballWordRepositoryCustomImpl implements BaseballWordRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BaseballWord> findBaseballWords(Integer pageSize, String cursorWord, String query) {

        QBaseballWord baseballWord = QBaseballWord.baseballWord;
        BooleanBuilder whereClause = getWhereClause(baseballWord, cursorWord, query);

        return queryFactory.selectFrom(baseballWord)
                .where(whereClause)
                .orderBy(baseballWord.word.asc())
                .limit(pageSize)
                .fetch();
    }

    private BooleanBuilder getWhereClause(QBaseballWord baseballWord, String cursorWord, String query) {
        BooleanBuilder whereClause = new BooleanBuilder();
        if (cursorWord != null) {
            whereClause.and(baseballWord.word.gt(cursorWord));
        }
        if (query != null) {
            whereClause.and(baseballWord.word.contains(query));
        }
        return whereClause;
    }
}
