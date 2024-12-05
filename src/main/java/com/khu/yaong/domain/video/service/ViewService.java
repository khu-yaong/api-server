package com.khu.yaong.domain.video.service;

import com.khu.yaong.domain.video.dto.VideoReqDTO;
import com.khu.yaong.domain.video.dto.VideoResDTO;

import java.util.concurrent.CompletableFuture;

public interface ViewService {

    VideoResDTO.UpdatedViewDTO getVideoAndUpdateView(String videoId);

    VideoResDTO.RecommendationDTO getRecommendedVideos();
}
