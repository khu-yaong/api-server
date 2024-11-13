package com.khu.yaong.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final List<String> EXCLUDE_URLS = Arrays.asList(
            "/swagger-ui", "/v3/api-docs", "/h2", "/api/auth/register","/api/auth/login","/login"
    );


    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = httpServletRequest.getRequestURI();
        if (EXCLUDE_URLS.stream().anyMatch(requestURI::startsWith)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String token = resolveToken(httpServletRequest);
        log.info("BearerToken: {}", token);

        try{
            if (token != null && jwtTokenProvider.isValidToken(token)){
                var authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Authentication set in SecurityContext.");
                System.out.println(authentication);
            } else {
                setErrorResponse(httpServletResponse, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.");
                return;
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (ExpiredJwtException e) {
            setErrorResponse(httpServletResponse,HttpStatus.UNAUTHORIZED,"토큰이 만료되었습니다.");
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            setErrorResponse(httpServletResponse, HttpStatus.BAD_REQUEST, "유효하지 않은 토큰 형식입니다.");
        } catch (Exception e) {
            setErrorResponse(httpServletResponse, HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");
        }
        }

    private void setErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value()); // HTTP 상태 코드 설정
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status.value());
        errorResponse.put("error", status.getReasonPhrase());
        errorResponse.put("message", message);

        String jsonResponse = objectMapper.writeValueAsString(errorResponse); // JSON 형식으로 변환
        response.getWriter().write(jsonResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        log.info("BearerToken: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

