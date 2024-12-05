package com.khu.yaong.domain.video.controller;

import com.khu.yaong.domain.video.dto.VideoResDTO;
import com.khu.yaong.domain.video.service.ViewService;
import com.khu.yaong.global.common.response.ApiResponse;
import com.khu.yaong.global.common.response.video.VideoSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Video API", description = "추천 클립 관련 API")
public class VideoController {

    //private final S3CsvService s3CsvService;
    private final ViewService viewService;

    @Operation(summary = "[구현완료] 영상 조회하기", description = """
    회원의 영상 조회 내역을 업데이트하고 새로운 추천 목록을 생성하도록 Lambda를 트리거합니다.
    """)
    @PostMapping("/videos")
    ApiResponse<VideoResDTO.UpdatedViewDTO> getVideoAndUpdateView(@RequestParam String videoId) {
        VideoResDTO.UpdatedViewDTO updatedViewDTO = viewService.getVideoAndUpdateView(videoId);
        return ApiResponse.success(VideoSuccessCode.VIDEO_VIEW_LIST_UPDATED, updatedViewDTO);
    }

}
