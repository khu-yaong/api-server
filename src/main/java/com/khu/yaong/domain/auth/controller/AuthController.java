package com.khu.yaong.domain.auth.controller;

import com.khu.yaong.domain.auth.dto.request.MemberEmailRequestDto;
import com.khu.yaong.domain.auth.dto.request.MemberEmailVerifyRequestDto;
import com.khu.yaong.domain.auth.dto.request.MemberLoginRequestDto;
import com.khu.yaong.domain.auth.dto.request.MemberRegisterRequestDto;
import com.khu.yaong.domain.auth.dto.response.MemberEmailResponseDto;
import com.khu.yaong.domain.auth.dto.response.MemberEmailVerifyResponseDto;
import com.khu.yaong.domain.auth.dto.response.MemberLoginResponseDto;
import com.khu.yaong.domain.auth.dto.response.MemberRegisterResponseDto;
import com.khu.yaong.domain.auth.exception.AuthSuccessCode;
import com.khu.yaong.domain.auth.service.AuthService;
import com.khu.yaong.domain.auth.service.GoogleOAuthService;
import com.khu.yaong.domain.auth.service.KakaoOAuthService;
import com.khu.yaong.global.common.response.ApiResponse;
import com.khu.yaong.global.s3.S3ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${cloud.aws.s3.default-profile}")
    private String defaultProfile;

    private final S3ImageService s3ImageService;
    private final AuthService authService;
    private final KakaoOAuthService kakaoOAuthService;
    private final GoogleOAuthService googleOAuthService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<MemberRegisterResponseDto>> register(
            @RequestPart MemberRegisterRequestDto request,
            @RequestPart(required = false) MultipartFile profileImage) {
        String imageUrl;
        if (profileImage != null) {
            String dir = "profile/";
            imageUrl = s3ImageService.uploadImage(dir, profileImage);
        } else {
            imageUrl = defaultProfile;
        }
        MemberRegisterResponseDto response = authService.register(request, imageUrl);
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

    @PostMapping("/sendEmail")
    public ResponseEntity<ApiResponse<MemberEmailResponseDto>> sendEmail(@RequestBody MemberEmailRequestDto requestDto) {
        MemberEmailResponseDto response = authService.sendCodeToEmail(requestDto.getEmail());
        return new ResponseEntity<>(ApiResponse.success(AuthSuccessCode.SEND_EMAIL_SUCCESS, response), HttpStatus.OK);

    }
    @PostMapping("/verifyEmail")
    public ResponseEntity<ApiResponse<MemberEmailVerifyResponseDto>> verifyEmail(@RequestBody MemberEmailVerifyRequestDto requestDto) {
        MemberEmailVerifyResponseDto response = authService.verifyEmailCode(requestDto.getEmail(), requestDto.getCode());
        return new ResponseEntity<>(ApiResponse.success(AuthSuccessCode.VERIFY_EMAIL_SUCCESS, response), HttpStatus.OK);

    }

}
