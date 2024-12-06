package com.khu.yaong.domain.video.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.member.domain.Team;
import com.khu.yaong.domain.member.exception.MemberErrorCode;
import com.khu.yaong.domain.member.exception.MemberException;
import com.khu.yaong.domain.member.repository.MemberRepository;
import com.khu.yaong.domain.video.domain.View;
import com.khu.yaong.domain.video.dto.VideoReqDTO;
import com.khu.yaong.domain.video.dto.VideoResDTO;
import com.khu.yaong.domain.video.repository.ViewRepository;
import com.khu.yaong.global.common.exception.BaseException;
import com.khu.yaong.global.common.response.ErrorCode;
import com.khu.yaong.global.s3.S3JsonService;
import com.khu.yaong.global.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
    public VideoResDTO.RecommendationDTO getRecommendedVideos(String filepath) {

        // Authorization
        Long memberId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        try {
            String filename = "videos/videos_for_member" + memberId + ".json";
            String jsonContent = s3JsonService.downloadJson(filename);
            ObjectMapper objectMapper = new ObjectMapper();

            VideoResDTO.RecommendationMapDTO recommendationMapDTO = objectMapper.readValue(jsonContent, VideoResDTO.RecommendationMapDTO.class);
            return VideoResDTO.RecommendationDTO.toDTO(recommendationMapDTO);
        } catch (Exception e) {
            return getTeamVideos(filepath);
        }
    }

    public VideoResDTO.RecommendationDTO getTeamVideos(String filePath) {

        // Authorization
        Long memberId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        try (InputStreamReader reader = new InputStreamReader(new ClassPathResource(filePath).getInputStream())) {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim());

            List<VideoResDTO.RecommendedVideoDTO> mapDTOS = new ArrayList<>();
            for (final CSVRecord csvRecord : csvParser) {
                Team team = Team.toEnum(csvRecord.get("team"));

                if (team != null && team.equals(member.getTeam())) {
                    VideoResDTO.RecommendedVideoDTO recommendedVideoMapDTO =
                            VideoResDTO.RecommendedVideoDTO.builder()
                                    .team(team)
                                    .videoId(csvRecord.get("videoId"))
                                    .title(csvRecord.get("title"))
                                    .thumbnail(csvRecord.get("thumbnail"))
                                    .build();
                    mapDTOS.add(recommendedVideoMapDTO);
                }
            }

            return VideoResDTO.RecommendationDTO.builder()
                    .recommendVideos(mapDTOS)
                    .build();
        } catch (IOException e) {
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
