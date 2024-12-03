package com.khu.yaong.domain.mp3.controller;

import com.khu.yaong.domain.data.dto.PlayerResDTO;
import com.khu.yaong.domain.mp3.domain.Mp3File;
import com.khu.yaong.domain.mp3.dto.response.Mp3FileResDto;
import com.khu.yaong.domain.mp3.repository.Mp3FileRepository;
import com.khu.yaong.domain.mp3.service.Mp3DatabaseService;
import com.khu.yaong.global.common.response.ApiResponse;
import com.khu.yaong.global.common.response.ErrorCode;
import com.khu.yaong.global.common.response.SuccessCode;
import com.khu.yaong.global.common.response.comment.CommentSuccessCode;
import com.khu.yaong.global.common.response.data.DataSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mp3-files")
public class Mp3Controller {

    private final Mp3DatabaseService mp3DatabaseService;

    @GetMapping("/sync")
    public ApiResponse<String> syncS3FilesToDatabase() {
        mp3DatabaseService.saveS3FilesToDatabase();
        return ApiResponse.success(SuccessCode.SUCCESS, "S3 files synchronized to database");
    }

    // 모든 MP3 파일 조회
    @GetMapping("/all")
    public ApiResponse<List<Mp3File>> getAllMp3Files() {
        List<Mp3File> files = mp3DatabaseService.getAllMp3Files();
        return ApiResponse.success(SuccessCode.SUCCESS, files);
    }

    // 특정 MP3 파일 조회
    @GetMapping("/{teamName}/{category}/{name}")
    public ApiResponse<Mp3FileResDto> getMp3File(
            @PathVariable("teamName") String teamName,
            @PathVariable("category") String category,
            @PathVariable("name") String name
    ) {

        Mp3FileResDto mp3FileResDto = mp3DatabaseService.getMp3File(teamName + "_" + category + "_" + name);

        // 로직 구현
        return ApiResponse.success(SuccessCode.SUCCESS, mp3FileResDto);
    }

    // 팀 공식/선수 응원가 목록 조회
    @GetMapping("/{teamName}/{category}")
    public ApiResponse<List<Mp3FileResDto>> getPlayerMp3ByPlayer(
            @PathVariable String teamName,
            @PathVariable String category
            ) {
        List<Mp3FileResDto> songs = mp3DatabaseService.getSongsByTeamAndCategory(teamName, category);
        return ApiResponse.success(SuccessCode.SUCCESS, songs);
    }
}
