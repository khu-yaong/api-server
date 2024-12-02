package com.khu.yaong.domain.mp3.controller;

import com.khu.yaong.domain.mp3.domain.Mp3File;
import com.khu.yaong.domain.mp3.repository.Mp3FileRepository;
import com.khu.yaong.domain.mp3.service.Mp3DatabaseService;
import com.khu.yaong.global.common.response.ApiResponse;
import com.khu.yaong.global.common.response.SuccessCode;
import com.khu.yaong.global.common.response.comment.CommentSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mp3-files")
public class Mp3Controller {

    private final Mp3DatabaseService mp3DatabaseService;

    @GetMapping("/sync")
    public ResponseEntity<String> syncS3FilesToDatabase() {
        mp3DatabaseService.saveS3FilesToDatabase();
        return ResponseEntity.ok("S3 files synchronized to database");
    }
    // 모든 MP3 파일 조회
    @GetMapping("/all")
    public ApiResponse<List<Mp3File>> getAllMp3Files() {
        List<Mp3File> files = mp3DatabaseService.getAllMp3Files();
        return ApiResponse.success(SuccessCode.SUCCESS, files);
    }
}
