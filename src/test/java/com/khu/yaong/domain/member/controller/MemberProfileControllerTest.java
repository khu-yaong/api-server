package com.khu.yaong.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khu.yaong.domain.member.controller.MemberProfileController;
import com.khu.yaong.domain.member.domain.Team;
import com.khu.yaong.domain.member.dto.MemberProfileResponseDto;
import com.khu.yaong.domain.member.exception.MemberNotFoundException;
import com.khu.yaong.domain.member.service.MemberProfileService;
import com.khu.yaong.global.auth.CustomUserDetails;
import com.khu.yaong.global.config.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class MemberProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberProfileService memberProfileService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // SecurityContext에 인증된 CustomUserDetails 설정
        CustomUserDetails userDetails = new CustomUserDetails(
                1L,
                "testuser",
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
    }

    @Test
    void testGetMemberProfile_Success() throws Exception {
        // given
        MemberProfileResponseDto responseDto = MemberProfileResponseDto.builder()
                .username("testuser")
                .team(Team.SSG)
                .profileImageUrl("http://example.com/image.jpg")
                .level(5)
                .experience(72)
                .nextLevelExp(100)
                .build();

        // when
        when(memberProfileService.getMemberProfile(anyLong())).thenReturn(responseDto);

        // then
        mockMvc.perform(get("/users/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.team").value(Team.SSG.toString()))
                .andExpect(jsonPath("$.profileImageUrl").value("http://example.com/image.jpg"))
                .andExpect(jsonPath("$.level").value(5))
                .andExpect(jsonPath("$.experience").value(72))
                .andExpect(jsonPath("$.nextLevelExp").value(100));
    }

    @Test
    void testGetMemberProfile_NotFound() throws Exception {
        // when
        when(memberProfileService.getMemberProfile(anyLong())).thenThrow(new MemberNotFoundException("Member not found"));

        // then
        mockMvc.perform(get("/users/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
