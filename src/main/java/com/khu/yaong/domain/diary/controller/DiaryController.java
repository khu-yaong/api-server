package com.khu.yaong.domain.diary.controller;

import com.khu.yaong.domain.diary.domain.Diary;
import com.khu.yaong.domain.diary.dto.DiaryRequestDto;
import com.khu.yaong.domain.diary.dto.DiaryResponseDto;
import com.khu.yaong.domain.diary.service.DiaryService;
import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.member.service.MemberProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;
    private final MemberProfileService memberProfileService;

    // 야구 관람 일지 생성
    @PostMapping("/game_diaries")
    public ResponseEntity<DiaryResponseDto> createDiary(@RequestParam Long memberId, @RequestBody DiaryRequestDto requestDto) {
        DiaryResponseDto responseDto = diaryService.createDiary(memberId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 관람 일지 상세 조회
    @GetMapping("/game_diaries/{record_id}")
    public ResponseEntity<DiaryResponseDto> getDiary(@PathVariable("record_id") Long diaryId) {
        DiaryResponseDto responseDto = diaryService.getDiaryById(diaryId);
        return ResponseEntity.ok(responseDto);
    }

    // 모든 관람 일지 목록 조회
    @GetMapping("/game_diaries")
    public ResponseEntity<List<DiaryResponseDto>> getAllDiaries(@RequestParam Long memberId) {
        List<DiaryResponseDto> responseDtoList = diaryService.getAllDiaries(memberId);
        return ResponseEntity.ok(responseDtoList);
    }

    // 관람 일지 내용 수정
    @PutMapping("/game_diaries/{record_id}")
    public ResponseEntity<DiaryResponseDto> updateDiary(@PathVariable("record_id") Long diaryId, @RequestBody DiaryRequestDto requestDto) {
        DiaryResponseDto responseDto = diaryService.updateDiary(diaryId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 관람 일지 삭제
    @DeleteMapping("/game_diaries/{record_id}")
    public ResponseEntity<Void> deleteDiary(@PathVariable("record_id") Long diaryId) {
        diaryService.deleteDiary(diaryId);
        return ResponseEntity.noContent().build();
    }
}
