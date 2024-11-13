package com.khu.yaong.domain.diary.service;

import com.khu.yaong.domain.diary.domain.Diary;
import com.khu.yaong.domain.diary.dto.DiaryRequestDto;
import com.khu.yaong.domain.diary.dto.DiaryResponseDto;
import com.khu.yaong.domain.diary.exception.DiaryNotFoundException;
import com.khu.yaong.domain.diary.repository.DiaryRepository;
import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.post.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;


    // 관람 일지 생성
    @Transactional
    public DiaryResponseDto createDiary(Long memberId, DiaryRequestDto requestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + memberId));

        Diary diary = Diary.builder()
                .member(member)
                .matchDate(requestDto.getMatchDate())
                .place(requestDto.getPlace())
                .team1(requestDto.getTeam1())
                .team2(requestDto.getTeam2())
                .team1Score(requestDto.getTeam1Score())
                .team2Score(requestDto.getTeam2Score())
                .seat(requestDto.getSeat())
                .content(requestDto.getContent())
                .imageUrl(requestDto.getImageUrl())
                .build();
        diaryRepository.save(diary);
        return new DiaryResponseDto(diary);
    }

    // 단일 관람 일지 조회
    @Transactional(readOnly = true)
    public DiaryResponseDto getDiaryById(Long recordId) {
        Diary diary = diaryRepository.findById(recordId)
                .orElseThrow(() -> new DiaryNotFoundException("Diary not found with id: " + recordId));
        return new DiaryResponseDto(diary);
    }


    // 모든 관람 일지 조회
    @Transactional(readOnly = true)
    public List<DiaryResponseDto> getAllDiaries(Long memberId) {
        List<Diary> diaries = diaryRepository.findAllByMemberId(memberId);
        return diaries.stream()
                .map(DiaryResponseDto::new)
                .collect(Collectors.toList());
    }

    // 관람 일지 수정
    @Transactional
    public DiaryResponseDto updateDiary(Long diaryId, DiaryRequestDto requestDto) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new DiaryNotFoundException("Diary not found with id: " + diaryId));

        diary.updateDiary(
                requestDto.getMatchDate(),
                requestDto.getPlace(),
                requestDto.getTeam1(),
                requestDto.getTeam1Score(),
                requestDto.getTeam2(),
                requestDto.getTeam2Score(),
                requestDto.getSeat(),
                requestDto.getContent(),
                requestDto.getImageUrl()
        );
        return new DiaryResponseDto(diary);
    }

    // 관람 일지 삭제
    @Transactional
    public void deleteDiary(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new DiaryNotFoundException("Diary not found with id: " + diaryId));
        diaryRepository.delete(diary);
    }

}
