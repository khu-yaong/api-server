package com.khu.yaong.domain.diary.service;

import com.khu.yaong.domain.diary.domain.Diary;
import com.khu.yaong.domain.diary.dto.DiaryRequestDto;
import com.khu.yaong.domain.diary.dto.DiaryResponseDto;
import com.khu.yaong.domain.diary.exception.DiaryNotFoundException;
import com.khu.yaong.domain.diary.repository.DiaryRepository;
import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.member.domain.Team;
import com.khu.yaong.domain.post.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class DiaryServiceTest {

    @InjectMocks
    private DiaryService diaryService;

    @Mock
    private DiaryRepository diaryRepository;

    @Mock
    private MemberRepository memberRepository;

    private Member member;
    private Diary diary;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        member = Member.builder()
                .id(1L)
                .username("testuser")
                .password("password")
                .email("test@example.com")
                .build();

        diary = Diary.builder()
                .id(1L)
                .member(member)
                .matchDate(LocalDate.now())
                .place("SSG 랜더스 필드")
                .team1("SSG")
                .team1Score(5)
                .team2("롯데")
                .team2Score(3)
                .seat("412블록 P열 11번")
                .content("경기 관람 내용")
                .imageUrl("http://example.com/image.jpg")
                .build();
    }

    @Test
    void createDiary_Success() {
        DiaryRequestDto requestDto = new DiaryRequestDto(
                LocalDate.now(), "SSG 랜더스 필드", Team.SSG.toString(), 5, Team.LOTTE.toString(), 3, "412블록 P열 11번", "좋은 경기!", "http://image.com"
        );
        Diary diary = new Diary();

        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        when(diaryRepository.save(any(Diary.class))).thenReturn(diary);

        DiaryResponseDto responseDto = diaryService.createDiary(1L, requestDto);

        assertNotNull(responseDto);
        verify(diaryRepository, times(1)).save(any(Diary.class));
    }

    @Test
    void createDiary_MemberNotFound() {
        DiaryRequestDto requestDto = new DiaryRequestDto(
                LocalDate.now(), "SSG 랜더스 필드", Team.SSG.toString(), 5, Team.LOTTE.toString(), 3, "412블록 P열 11번", "좋은 경기!", "http://image.com"
        );

        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> diaryService.createDiary(1L, requestDto));
        assertEquals("Member not found with id: 1", exception.getMessage());
    }

    @Test
    void getDiaryById_Success() {
        // given
        Member member = Member.builder()
                .id(1L)
                .username("testuser")
                .password("password")
                .email("test@example.com")
                .build();

        Diary diary = Diary.builder()
                .id(1L)
                .member(member) // member 설정
                .matchDate(LocalDate.now())
                .place("SSG 랜더스 필드")
                .team1("SSG")
                .team1Score(5)
                .team2("롯데")
                .team2Score(3)
                .seat("412블록 P열 11번")
                .content("경기 관람 내용")
                .imageUrl("http://example.com/image.jpg")
                .build();

        when(diaryRepository.findById(anyLong())).thenReturn(Optional.of(diary));

        // when
        DiaryResponseDto responseDto = diaryService.getDiaryById(1L);

        // then
        assertNotNull(responseDto);
        assertEquals(1L, responseDto.getMemberId());
        verify(diaryRepository, times(1)).findById(anyLong());
    }

    @Test
    void getDiaryById_NotFound() {
        when(diaryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(DiaryNotFoundException.class, () -> diaryService.getDiaryById(1L));
    }

    @Test
    void getAllDiaries_Success() {
        // given
        when(diaryRepository.findAllByMemberId(anyLong())).thenReturn(List.of(diary));

        // when
        List<DiaryResponseDto> diaries = diaryService.getAllDiaries(member.getId());

        // then
        assertNotNull(diaries);
        assertEquals(1, diaries.size());
        verify(diaryRepository, times(1)).findAllByMemberId(member.getId());
    }

    @Test
    void updateDiary_Success() {
        // given
        DiaryRequestDto requestDto = new DiaryRequestDto(
                LocalDate.now(),
                "업데이트된 장소",
                "SSG",
                7,
                "롯데",
                4,
                "업데이트된 좌석",
                "업데이트된 내용",
                "http://example.com/updated_image.jpg"
        );


        when(diaryRepository.findById(anyLong())).thenReturn(Optional.of(diary));

        // when
        DiaryResponseDto responseDto = diaryService.updateDiary(diary.getId(), requestDto);

        // then
        assertNotNull(responseDto);
        assertEquals("업데이트된 장소", responseDto.getPlace());
        verify(diaryRepository, times(1)).findById(diary.getId());
    }

    @Test
    void updateDiary_NotFound() {
        DiaryRequestDto requestDto = new DiaryRequestDto(
                LocalDate.now(), "SSG 랜더스 필드", Team.SSG.toString(), 5, Team.LOTTE.toString(), 3, "412블록 P열 11번", "좋은 경기!", "http://image.com"
        );

        when(diaryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(DiaryNotFoundException.class, () -> diaryService.updateDiary(1L, requestDto));
    }

    @Test
    void deleteDiary_Success() {
        Diary diary = new Diary();
        when(diaryRepository.findById(anyLong())).thenReturn(Optional.of(diary));

        diaryService.deleteDiary(1L);

        verify(diaryRepository, times(1)).delete(diary);
    }

    @Test
    void deleteDiary_NotFound() {
        when(diaryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(DiaryNotFoundException.class, () -> diaryService.deleteDiary(1L));
    }
}
