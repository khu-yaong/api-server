package com.khu.yaong.domain.diary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khu.yaong.domain.diary.dto.DiaryRequestDto;
import com.khu.yaong.domain.diary.dto.DiaryResponseDto;
import com.khu.yaong.domain.diary.service.DiaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class DiaryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiaryService diaryService;

    @Autowired
    private ObjectMapper objectMapper;

    private DiaryRequestDto diaryRequestDto;
    private DiaryResponseDto diaryResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        diaryRequestDto = new DiaryRequestDto(
                LocalDate.now(),
                "SSG 랜더스 필드",
                "SSG",
                5,
                "롯데",
                3,
                "412블록 P열 11번",
                "경기 관람 후기 내용",
                "http://example.com/image.jpg"
        );

        diaryResponseDto = new DiaryResponseDto(
                1L,
                1L,
                LocalDate.now(),
                "SSG 랜더스 필드",
                "SSG",
                5,
                "롯데",
                3,
                "412블록 P열 11번",
                "경기 관람 후기 내용",
                "http://example.com/image.jpg"
        );
    }

    @Test
    void createDiary_Success() throws Exception {
        when(diaryService.createDiary(anyLong(), any(DiaryRequestDto.class))).thenReturn(diaryResponseDto);

        mockMvc.perform(post("/users/me/game_diaries")
                        .param("memberId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diaryRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(diaryResponseDto.getId()))
                .andExpect(jsonPath("$.memberId").value(diaryResponseDto.getMemberId()))
                .andExpect(jsonPath("$.matchDate").value(diaryResponseDto.getMatchDate().toString()))
                .andExpect(jsonPath("$.place").value(diaryResponseDto.getPlace()))
                .andExpect(jsonPath("$.team1").value(diaryResponseDto.getTeam1()))
                .andExpect(jsonPath("$.team1Score").value(diaryResponseDto.getTeam1Score()))
                .andExpect(jsonPath("$.team2").value(diaryResponseDto.getTeam2()))
                .andExpect(jsonPath("$.team2Score").value(diaryResponseDto.getTeam2Score()))
                .andExpect(jsonPath("$.seat").value(diaryResponseDto.getSeat()))
                .andExpect(jsonPath("$.content").value(diaryResponseDto.getContent()))
                .andExpect(jsonPath("$.imageUrl").value(diaryResponseDto.getImageUrl()));
    }



    @Test
    void getDiaryById_Success() throws Exception {
        when(diaryService.getDiaryById(anyLong())).thenReturn(diaryResponseDto);

        mockMvc.perform(get("/users/me/game_diaries/{record_id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(diaryResponseDto.getId()))
                .andExpect(jsonPath("$.memberId").value(diaryResponseDto.getMemberId()))
                .andExpect(jsonPath("$.matchDate").value(diaryResponseDto.getMatchDate().toString()))
                .andExpect(jsonPath("$.place").value(diaryResponseDto.getPlace()))
                .andExpect(jsonPath("$.team1").value(diaryResponseDto.getTeam1()))
                .andExpect(jsonPath("$.team1Score").value(diaryResponseDto.getTeam1Score()))
                .andExpect(jsonPath("$.team2").value(diaryResponseDto.getTeam2()))
                .andExpect(jsonPath("$.team2Score").value(diaryResponseDto.getTeam2Score()))
                .andExpect(jsonPath("$.seat").value(diaryResponseDto.getSeat()))
                .andExpect(jsonPath("$.content").value(diaryResponseDto.getContent()))
                .andExpect(jsonPath("$.imageUrl").value(diaryResponseDto.getImageUrl()));
    }

    @Test
    void getAllDiaries_Success() throws Exception {
        List<DiaryResponseDto> diaries = Arrays.asList(diaryResponseDto, diaryResponseDto);
        when(diaryService.getAllDiaries(anyLong())).thenReturn(diaries);

        mockMvc.perform(get("/users/me/game_diaries")
                        .param("memberId", "1") // 필요한 경우 memberId 추가
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(diaries.size()))
                .andExpect(jsonPath("$[0].id").value(diaryResponseDto.getId()))
                .andExpect(jsonPath("$[0].memberId").value(diaryResponseDto.getMemberId()));
    }

    @Test
    void updateDiary_Success() throws Exception {
        when(diaryService.updateDiary(anyLong(), any(DiaryRequestDto.class))).thenReturn(diaryResponseDto);

        mockMvc.perform(put("/users/me/game_diaries/{record_id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diaryRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(diaryResponseDto.getId()))
                .andExpect(jsonPath("$.place").value(diaryResponseDto.getPlace()))
                .andExpect(jsonPath("$.team1Score").value(diaryResponseDto.getTeam1Score()))
                .andExpect(jsonPath("$.team2Score").value(diaryResponseDto.getTeam2Score()))
                .andExpect(jsonPath("$.seat").value(diaryResponseDto.getSeat()))
                .andExpect(jsonPath("$.content").value(diaryResponseDto.getContent()));
    }

    @Test
    void deleteDiary_Success() throws Exception {
        mockMvc.perform(delete("/users/me/game_diaries/{record_id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // 상태 코드를 204로 수정
    }

}
