package com.khu.yaong.domain.data.repository.querydsl;

import com.khu.yaong.domain.data.domain.BaseballWord;

import java.util.List;

public interface BaseballWordRepositoryCustom {

    List<BaseballWord> findBaseballWords(Integer pageSize, String cursorWord, String query);
}
