package com.khu.yaong.domain.data.service;

import com.khu.yaong.domain.data.dto.WordResDTO;
import com.khu.yaong.domain.data.repository.BaseballWordRepository;
import com.khu.yaong.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WordServiceImpl implements WordService {

    private final BaseballWordRepository baseballWordRepository;

    @Override
    public List<WordResDTO.WordDescriptionDTO> getBaseballWords(Integer pageSize, String cursorWord, String query) {

        // Authorization
        SecurityUtil.getCurrentMemberId();

        return baseballWordRepository.findBaseballWords(pageSize, cursorWord, query).stream()
                .map(WordResDTO.WordDescriptionDTO::toDTO)
                .toList();
    }
}
