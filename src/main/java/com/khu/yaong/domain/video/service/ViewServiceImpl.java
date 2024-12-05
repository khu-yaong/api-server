package com.khu.yaong.domain.video.service;

import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.member.exception.MemberErrorCode;
import com.khu.yaong.domain.member.exception.MemberException;
import com.khu.yaong.domain.member.repository.MemberRepository;
import com.khu.yaong.domain.video.domain.View;
import com.khu.yaong.domain.video.dto.VideoReqDTO;
import com.khu.yaong.domain.video.dto.VideoResDTO;
import com.khu.yaong.domain.video.repository.ViewRepository;
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

    @Override
    public VideoResDTO.UpdatedViewDTO getVideoAndUpdateView(String videoId) {

        System.out.println("getVideoAndUpdateView started");

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
}
