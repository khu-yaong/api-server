package com.khu.yaong.domain.video.controller;

import com.khu.yaong.domain.member.domain.Team;
import com.khu.yaong.domain.video.dto.VideoResDTO;
import com.khu.yaong.domain.video.service.ViewService;
import com.khu.yaong.global.common.response.ApiResponse;
import com.khu.yaong.global.common.response.video.VideoSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Video API", description = "추천 클립 관련 API")
public class VideoController {

    private final ViewService viewService;

    String filepath = "data/video_raw_data.csv";

    @Operation(summary = "[구현완료] 영상 조회하기", description = """
    회원의 영상 조회 내역을 업데이트하고 새로운 추천 목록을 생성하도록 Lambda를 트리거합니다.
    * videoId(String) : 유튜브 동영상 id
    """)
    @PostMapping("/videos")
    ApiResponse<VideoResDTO.UpdatedViewDTO> getVideoAndUpdateView(@RequestParam String videoId) {
        VideoResDTO.UpdatedViewDTO updatedViewDTO = viewService.getVideoAndUpdateView(videoId);
        return ApiResponse.success(VideoSuccessCode.VIDEO_VIEW_LIST_UPDATED, updatedViewDTO);
    }

    @Operation(summary = "[구현완료] 추천 영상 목록 조회하기", description = """
    회원 맞춤 추천 영상 목록을 조회합니다.
    """)
    @GetMapping("/videos/recommendation")
    ApiResponse<VideoResDTO.RecommendationDTO> getRecommendedVideos() {
        VideoResDTO.RecommendationDTO recommendationDTO = viewService.getRecommendedVideos(filepath);
        return ApiResponse.success(VideoSuccessCode.RECOMMENDED_VIDEOS_FOUND, recommendationDTO);
    }

    @Operation(summary = "[구현완료] 응원 구단 영상 목록 조회하기", description = """
    회원이 응원하는 구단의 영상 목록을 조회합니다.
    """)
    @GetMapping("/videos")
    ApiResponse<VideoResDTO.RecommendationDTO> getTeamVideos() {
        VideoResDTO.RecommendationDTO recommendationDTO = viewService.getTeamVideos(filepath);
        return ApiResponse.success(VideoSuccessCode.TEAM_VIDEOS_FOUND, recommendationDTO);
    }

}
