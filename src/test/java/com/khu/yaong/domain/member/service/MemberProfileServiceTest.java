package com.khu.yaong.domain.member.service;

import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.member.domain.MemberLevel;
import com.khu.yaong.domain.member.domain.MemberRole;
import com.khu.yaong.domain.member.domain.Team;
import com.khu.yaong.domain.member.dto.MemberProfileResponseDto;
import com.khu.yaong.domain.member.repository.MemberProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MemberProfileServiceTest {

    @Mock
    private MemberProfileRepository memberProfileRepository;

    @InjectMocks
    private MemberProfileService memberProfileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getMemberProfile_whenMemberExist_thenReturnProfileResponse() {
        //given
        Long memberId = 1L;

        MemberLevel memberLevel = MemberLevel.builder()
                .level(5)
                .exp(72)
                .nextLevelExp(100)
                .build();

        Member member = Member.builder()
                .id(memberId)
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .team(Team.SSG)
                .profileImage("http://example.com/image.jpg")
                .role(MemberRole.USER)
                .memberLevel(memberLevel)
                .build();

        memberLevel.setMember(member);

        when(memberProfileRepository.findById(memberId)).thenReturn(Optional.of(member));

        //when
        MemberProfileResponseDto profileResponseDto = memberProfileService.getMemberProfile(memberId);

        // then
        assertNotNull(profileResponseDto);
        assertEquals("testuser", profileResponseDto.getUsername());
        assertEquals(Team.SSG, profileResponseDto.getTeam());
        assertEquals("http://example.com/image.jpg", profileResponseDto.getProfileImageUrl());
        assertEquals(5, profileResponseDto.getLevel());
        assertEquals(72, profileResponseDto.getExperience());
        assertEquals(100, profileResponseDto.getNextLevelExp());
    }
}