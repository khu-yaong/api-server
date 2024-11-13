package com.khu.yaong.global.security.jwt;

import com.khu.yaong.domain.member.domain.MemberRole;
import com.khu.yaong.global.security.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final long EXPIRATION_TIME = 86400000;
    @Value("${jwt.secret.key}")
    private String SECRETKEY;

    private final CustomUserDetailsService userDetailsService;

    public String createToken(Long memberId, MemberRole memberRole){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .claim("role", "ROLE_"+memberRole.toString())
                .setSubject(memberId.toString())
                .setIssuedAt(now)
                .setExpiration(expiration)
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
        return MemberRole.valueOf(getClaims(token).getSubject());
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

}
