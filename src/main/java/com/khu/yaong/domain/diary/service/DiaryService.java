package com.khu.yaong.domain.diary.service;

import com.khu.yaong.domain.diary.domain.Diary;
import com.khu.yaong.domain.diary.dto.DiaryRequestDto;
import com.khu.yaong.domain.diary.dto.DiaryResponseDto;
import com.khu.yaong.domain.diary.exception.DiaryNotFoundException;
import com.khu.yaong.domain.diary.repository.DiaryRepository;
import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.member.exception.MemberErrorCode;
import com.khu.yaong.domain.member.exception.MemberException;
import com.khu.yaong.domain.member.repository.MemberRepository;
import com.khu.yaong.global.s3.S3ImageService;
import com.khu.yaong.global.security.CustomUserDetails;
import com.khu.yaong.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;

    private final S3ImageService s3ImageService;


    // 관람 일지 생성
    @Transactional
    public DiaryResponseDto createDiary(DiaryRequestDto requestDto, String imageUrl) {

        // Authorization
        Long memberId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    s3ImageService.deleteImageFromS3(imageUrl);
                    return new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
                });

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
                .imageUrl(imageUrl)
                .build();
        diaryRepository.save(diary);
        return new DiaryResponseDto(diary);
    }

    // 단일 관람 일지 조회
    @Transactional(readOnly = true)
    public DiaryResponseDto getDiaryByMatchDate(LocalDate date) {
        Diary diary = diaryRepository.findByMatchDate(date)
                .orElseThrow(() -> new DiaryNotFoundException("Diary not found with date: " + date));
        return new DiaryResponseDto(diary);
    }

    // 모든 관람 일지 조회
    @Transactional(readOnly = true)
    public List<DiaryResponseDto> getAllDiaries(Long memberId){
        List<Diary> diaries = diaryRepository.findAllByMemberId(memberId);
        return diaries.stream().map(DiaryResponseDto::new)
                .collect(Collectors.toList());
    }
    // 관람 일지 수정
    @Transactional
    public DiaryResponseDto updateDiary(Long diaryId, DiaryRequestDto requestDto, String imageUrl) {

        // Authorization
        Long memberId = SecurityUtil.getCurrentMemberId();
        memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    s3ImageService.deleteImageFromS3(imageUrl);
                    return new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
                });

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> {
                    s3ImageService.deleteImageFromS3(imageUrl);
                    return new DiaryNotFoundException("Diary not found with id: " + diaryId);
                });

        String existingImageUrl = diary.getImageUrl();

        diary.updateDiary(
                requestDto.getMatchDate(),
                requestDto.getPlace(),
                requestDto.getTeam1(),
                requestDto.getTeam1Score(),
                requestDto.getTeam2(),
                requestDto.getTeam2Score(),
                requestDto.getSeat(),
                requestDto.getContent(),
                imageUrl
        );
        diaryRepository.save(diary);

        // 기존 프로필 이미지 삭제
        s3ImageService.deleteImageFromS3(existingImageUrl);

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
