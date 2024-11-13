package com.khu.yaong.domain.member.service;

import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.member.dto.MemberProfileResponseDto;
import com.khu.yaong.domain.member.exception.MemberNotFoundException;
import com.khu.yaong.domain.member.repository.MemberProfileRepository;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class MemberProfileService {

    private final MemberProfileRepository memberProfileRepository;

    public MemberProfileService(MemberProfileRepository memberProfileRepository){
        this.memberProfileRepository = memberProfileRepository;
    }

    public MemberProfileResponseDto getMemberProfile(Long memberId){
        Member member = memberProfileRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("User not found"));

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
