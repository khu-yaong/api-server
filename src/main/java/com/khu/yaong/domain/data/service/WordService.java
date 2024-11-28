package com.khu.yaong.domain.data.service;

import com.khu.yaong.domain.data.dto.WordResDTO;

import java.util.List;

public interface WordService {

    List<WordResDTO.WordDescriptionDTO> getBaseballWords(Integer pageSize, String cursorWord, String query);
}
