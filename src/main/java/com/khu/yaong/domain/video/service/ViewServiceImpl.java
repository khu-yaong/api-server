package com.khu.yaong.domain.video.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.member.exception.MemberErrorCode;
import com.khu.yaong.domain.member.exception.MemberException;
import com.khu.yaong.domain.member.repository.MemberRepository;
import com.khu.yaong.domain.video.domain.View;
import com.khu.yaong.domain.video.dto.VideoReqDTO;
import com.khu.yaong.domain.video.dto.VideoResDTO;
import com.khu.yaong.domain.video.repository.ViewRepository;
import com.khu.yaong.global.common.exception.BaseException;
import com.khu.yaong.global.common.response.s3.S3ErrorCode;
import com.khu.yaong.global.s3.S3JsonService;
import com.khu.yaong.global.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ViewServiceImpl implements ViewService {

    private final MemberRepository memberRepository;
    private final ViewRepository viewRepository;
    private final LambdaInvoker lambdaInvoker;

    private final S3JsonService s3JsonService;

    @Override
    public VideoResDTO.UpdatedViewDTO getVideoAndUpdateView(String videoId) {

        // Authorization
        Long memberId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Optional<View> optionalView = viewRepository.findByMemberIdAndVideoId(memberId, videoId);

        // 시청하지 않은 영상에 대해 업데이트
        if (optionalView.isEmpty()) {
            View view = View.builder()
                    .videoId(videoId)
                    .member(member)
                    .build();

            View savedView = viewRepository.save(view);
            member.addView(savedView);

            VideoReqDTO.LambdaRequestDTO lambdaRequestDTO = VideoReqDTO.LambdaRequestDTO.toDTO(member);
            lambdaInvoker.updateRecommendVideos(lambdaRequestDTO);
            return VideoResDTO.UpdatedViewDTO.toDTO(view);
        } else {
            return VideoResDTO.UpdatedViewDTO.toDTO(optionalView.get());
        }

    }

    @Override
    public VideoResDTO.RecommendationDTO getRecommendedVideos() {

        // Authorization
        Long memberId = SecurityUtil.getCurrentMemberId();
        memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        String filename = "videos/videos_for_member" + memberId + ".json";
        String jsonContent = s3JsonService.downloadJson(filename);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            VideoResDTO.RecommendationMapDTO recommendationMapDTO = objectMapper.readValue(jsonContent, VideoResDTO.RecommendationMapDTO.class);
            return VideoResDTO.RecommendationDTO.toDTO(recommendationMapDTO);
        } catch (Exception e) {
            throw new BaseException(S3ErrorCode.IO_EXCEPTION);
        }
    }
}
