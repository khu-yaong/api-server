package com.khu.yaong.domain.member.service;

import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.member.dto.response.MemberProfileResponseDto;
import com.khu.yaong.domain.member.exception.MemberErrorCode;
import com.khu.yaong.domain.member.exception.MemberException;
import com.khu.yaong.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberProfileResponseDto getMemberProfileById(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        return MemberProfileResponseDto.builder()
                .username(member.getUsername())
                .team(member.getTeam())
                .profileImageUrl(member.getProfileImage())
                .level(member.getMemberLevel() != null ? member.getMemberLevel().getLevel() :0)
                .experience(member.getMemberLevel() != null ? member.getMemberLevel().getExp() : 0)
                .nextLevelExp(member.getMemberLevel() != null ? member.getMemberLevel().getNextLevelExp() : 0)
                .build();
    }
}
