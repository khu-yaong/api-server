package com.khu.yaong.domain.diary.controller;

import com.khu.yaong.domain.diary.dto.DiaryRequestDto;
import com.khu.yaong.domain.diary.dto.DiaryResponseDto;
import com.khu.yaong.domain.diary.service.DiaryService;
import com.khu.yaong.global.s3.S3ImageService;
import com.khu.yaong.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
public class DiaryController {

    private final String dir = "diary/";
    private final S3ImageService s3ImageService;
    private final DiaryService diaryService;

    // 야구 관람 일지 생성
    @PostMapping(value = "/game_diaries", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DiaryResponseDto> createDiary(
            @RequestPart DiaryRequestDto requestDto,
            @RequestPart(required = false) MultipartFile image
    ) {
        String imageUrl;
        if (image == null || image.isEmpty()) {
            imageUrl = null;
        } else {
            imageUrl = s3ImageService.uploadImage(dir, image);
        }
        DiaryResponseDto responseDto = diaryService.createDiary(requestDto, imageUrl);
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
    public ResponseEntity<List<DiaryResponseDto>> getAllDiaries(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String authenticatedMemberId = userDetails.getUsername();  // 인증된 사용자 ID 가져오기
        List<DiaryResponseDto> responseDtoList = diaryService.getAllDiaries(Long.valueOf(authenticatedMemberId));
        return ResponseEntity.ok(responseDtoList);
    }

    // 관람 일지 내용 수정
    @PutMapping(value = "/game_diaries/{record_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DiaryResponseDto> updateDiary(
            @PathVariable("record_id") Long diaryId,
            @RequestPart DiaryRequestDto requestDto,
            @RequestPart(required = false) MultipartFile image
    ) {
        String imageUrl;
        if (image == null || image.isEmpty()) {
            imageUrl = null;
        } else {
            imageUrl = s3ImageService.uploadImage(dir, image);
        }
        DiaryResponseDto responseDto = diaryService.updateDiary(diaryId, requestDto, imageUrl);
        return ResponseEntity.ok(responseDto);
    }

    // 관람 일지 삭제
    @DeleteMapping("/game_diaries/{record_id}")
    public ResponseEntity<Void> deleteDiary(@PathVariable("record_id") Long diaryId) {
        diaryService.deleteDiary(diaryId);
        return ResponseEntity.noContent().build();
    }
}
