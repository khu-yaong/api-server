package com.khu.yaong.domain.auth.controller;

import com.khu.yaong.domain.auth.dto.request.MemberLoginRequestDto;
import com.khu.yaong.domain.auth.dto.request.MemberRegisterRequestDto;
import com.khu.yaong.domain.auth.dto.response.MemberLoginResponseDto;
import com.khu.yaong.domain.auth.dto.response.MemberRegisterResponseDto;
import com.khu.yaong.domain.auth.exception.AuthSuccessCode;
import com.khu.yaong.domain.auth.service.AuthService;
import com.khu.yaong.domain.auth.service.GoogleOAuthService;
import com.khu.yaong.domain.auth.service.KakaoOAuthService;
import com.khu.yaong.global.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
//@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final KakaoOAuthService kakaoOAuthService;
    //private final GoogleOAuthService googleOAuthService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<MemberRegisterResponseDto>> register(@RequestBody MemberRegisterRequestDto request) {
        MemberRegisterResponseDto response = authService.register(request);
        return new ResponseEntity<>(ApiResponse.success(AuthSuccessCode.REGISTER_SUCCESS, response), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<MemberLoginResponseDto>> login(@RequestBody MemberLoginRequestDto request) {
        MemberLoginResponseDto response = authService.login(request);
        return new ResponseEntity<>(ApiResponse.success(AuthSuccessCode.LOGIN_SUCCESS, response),HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<String>> refreshAccessToken(@RequestBody Map<String, String> requestBody) {
        String refreshToken = requestBody.get("refreshToken");
        String newAccessToken = authService.refreshAccessToken(refreshToken);

        return new ResponseEntity<>(ApiResponse.success(AuthSuccessCode.TOKEN_REFRESH_SUCCESS, newAccessToken), HttpStatus.OK);
    }

    @PostMapping("/login/kakao")
    public ResponseEntity<ApiResponse<MemberLoginResponseDto>> loginKakao(@RequestBody Map<String, String> requestBody) {
        String authorizationCode = requestBody.get("authorizationCode");
        MemberLoginResponseDto response = kakaoOAuthService.loginKakao(authorizationCode);
        return new ResponseEntity<>(ApiResponse.success(AuthSuccessCode.LOGIN_SUCCESS, response),HttpStatus.OK);
    }

    @PostMapping("/login/google")
    public ResponseEntity<ApiResponse<MemberLoginResponseDto>> loginGoogle(@RequestBody Map<String, String> requestBody) {
        String authorizationCode = requestBody.get("authorizationCode");
        MemberLoginResponseDto response = googleOAuthService.loginGoogle(authorizationCode);
        return new ResponseEntity<>(ApiResponse.success(AuthSuccessCode.LOGIN_SUCCESS, response), HttpStatus.OK);
    }

}
