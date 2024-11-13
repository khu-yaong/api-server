package com.khu.yaong.global.auth;

import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.post.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@AllArgsConstructor
@Getter
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new CustomUserDetails(
                member.getId(),
                member.getUsername(),
                member.getPassword(),
                member.getAuthorities()  // 권한 목록 설정
        );
    }

}

