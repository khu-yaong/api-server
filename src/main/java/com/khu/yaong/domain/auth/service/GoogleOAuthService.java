package com.khu.yaong.domain.auth.service;

import com.khu.yaong.domain.auth.dto.response.GoogleTokenResponse;
import com.khu.yaong.domain.auth.dto.response.GoogleUserInfo;
import com.khu.yaong.domain.auth.dto.response.MemberLoginResponseDto;
import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.member.repository.MemberRepository;
import com.khu.yaong.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GoogleOAuthService {

    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client-secret}")
    private String clientSecret;

    @Value("${google.redirect-uri}")
    private String redirectUri;

    @Value("${google.token-uri}")
    private String tokenUri;

    @Value("${google.user-info-uri}")
    private String userInfoUri;

    public MemberLoginResponseDto loginGoogle(String authorizationCode) {
        String accessToken = getAccessToken(authorizationCode);
        GoogleUserInfo googleUserInfo = getUserInfo(accessToken);

        Member member = authService.createMemberByGoogle(googleUserInfo);
        Optional<Member> savedMember = Optional.of(memberRepository.save(member));

        String token = jwtTokenProvider.createAccessToken(savedMember.get().getId(), savedMember.get().getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(savedMember.get().getId(),savedMember.get().getRole());
        return new MemberLoginResponseDto(token, refreshToken,savedMember.get().getId(), savedMember.get().getUsername(), savedMember.get().getRole(), savedMember.get().getProfileImage());
    }

    public String getAccessToken(String authorizationCode) {
        RestTemplate restTemplate = new RestTemplate();

        String requestUrl = UriComponentsBuilder.fromHttpUrl(tokenUri)
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("code", authorizationCode)
                .toUriString();

        GoogleTokenResponse response = restTemplate.postForObject(requestUrl, null, GoogleTokenResponse.class);
        return response != null ? response.getAccessToken() : null;
    }

    public GoogleUserInfo getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                userInfoUri,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        Map<String, Object> responseBody = response.getBody();
        String name = (String) responseBody.get("name");
        String email = (String) responseBody.get("email");

        return new GoogleUserInfo(name, email);
    }
}
