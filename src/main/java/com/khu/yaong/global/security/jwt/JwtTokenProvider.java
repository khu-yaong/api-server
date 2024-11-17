package com.khu.yaong.global.security.jwt;

import com.khu.yaong.domain.auth.exception.AuthErrorCode;
import com.khu.yaong.domain.auth.exception.AuthException;
import com.khu.yaong.domain.member.domain.MemberRole;
import com.khu.yaong.global.security.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import java.util.Date;

import static org.apache.commons.lang3.Range.is;
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    //private final long EXPIRATION_TIME = 86400000;

    private final UserDetailsService userDetailsService;
    @Value("${jwt.secret.key}")
    private String SECRETKEY;

    @Value("${jwt.access-token.expiration}")
    private long accessTokenExpiration;

    @Getter
    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    public String createAccessToken(Long memberId, MemberRole memberRole){
        return createToken(memberId, memberRole,accessTokenExpiration);
    }

    public String createRefreshToken(Long memberId, MemberRole memberRole){
        return createToken(memberId, memberRole,refreshTokenExpiration);
    }

    public String createToken(Long memberId, MemberRole memberRole,long expiration){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .claim("role", "ROLE_"+memberRole.toString())
                .setSubject(memberId.toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512,SECRETKEY.getBytes())
                .compact();
    }

    public Authentication getAuthentication(String token){
        Long memberId = getMemberIdFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(memberId.toString());
        System.out.println(userDetails);
        System.out.println(userDetails.getAuthorities());
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public Long getMemberIdFromToken(String token){
        return Long.valueOf(getClaims(token).getSubject());
    }

    public Claims getClaims(String token){
        return Jwts.parser().setSigningKey(SECRETKEY.getBytes()).parseClaimsJws(token).getBody();
    }

    public MemberRole getMemberRole(String token){
        String role = (String) getClaims(token).get("role");
        if (role.startsWith("ROLE_")) {
            role = role.substring(5);
        }
        return MemberRole.valueOf(role);
    }

    // 필터에서 사용하는 토큰 유효성 검사 메서드
    public boolean isValidToken(String token){
        try{
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRETKEY.getBytes())
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }

    }
    public boolean validateRefreshToken(String refreshToken){
        return isValidToken(refreshToken);
    }
    public String renewAccessToken(String refreshToken){
        if (!validateRefreshToken(refreshToken)) {
            throw new AuthException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }
        Long memberId = getMemberIdFromToken(refreshToken);
        MemberRole memberRole = getMemberRole(refreshToken);
        log.info("MemberRole" + memberRole);
        return createAccessToken(memberId, memberRole);
        }

}
