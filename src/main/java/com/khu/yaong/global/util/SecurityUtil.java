package com.khu.yaong.global.util;

import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.global.security.CustomUserDetails;
import com.khu.yaong.global.security.CustomUserDetailsService;
import com.khu.yaong.global.security.exception.SecurityException;
import com.khu.yaong.global.security.jwt.JwtErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {

    public static Long getCurrentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication: {}", authentication);
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new SecurityException(JwtErrorCode.AUTHENTICATION_NOT_FOUND);
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            String memberId = ((CustomUserDetails) principal).getUsername();
            log.info("memberId: {}", memberId);
            return Long.valueOf(memberId);
        } else if (principal instanceof String) {
            log.info(((String) principal));
            return Long.valueOf((String) principal);
        } else {
            throw new SecurityException(JwtErrorCode.UNKNOWN_PRINCIPAL_TYPE);
        }
    }
}