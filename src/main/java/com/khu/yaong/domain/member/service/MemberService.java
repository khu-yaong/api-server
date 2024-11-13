package com.khu.yaong.domain.member.service;

import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.member.dto.response.MemberInfoResponseDto;
import com.khu.yaong.domain.member.exception.MemberErrorCode;
import com.khu.yaong.domain.member.exception.MemberException;
import com.khu.yaong.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberInfoResponseDto getMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));  // 임시

        return new MemberInfoResponseDto(
                member.getId(),
                member.getUsername(),
                member.getEmail(),
                member.getRole(),
                member.getProfileImage()
        );
    }
}