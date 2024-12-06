package com.khu.yaong.domain.video.service;

import com.khu.yaong.domain.video.dto.VideoResDTO;

public interface ViewService {

    VideoResDTO.UpdatedViewDTO getVideoAndUpdateView(String videoId);

    VideoResDTO.RecommendationDTO getRecommendedVideos(String filepath);

    VideoResDTO.RecommendationDTO getTeamVideos(String filePath);
}
