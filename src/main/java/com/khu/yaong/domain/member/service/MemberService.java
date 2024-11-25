package com.khu.yaong.domain.member.service;

import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.member.dto.request.MemberProfileReqDto;
import com.khu.yaong.domain.member.dto.response.MemberProfileResponseDto;
import com.khu.yaong.domain.member.exception.MemberErrorCode;
import com.khu.yaong.domain.member.exception.MemberException;
import com.khu.yaong.domain.member.repository.MemberRepository;
import com.khu.yaong.domain.post.domain.Post;
import com.khu.yaong.domain.post.repository.PostRepository;
import com.khu.yaong.domain.post.repository.PostRepositoryCustomImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public MemberProfileResponseDto getMemberProfileById(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        List<Post> posts = postRepository.findByAuthorId(memberId);

        return MemberProfileResponseDto.builder()
                .username(member.getUsername())
                .team(member.getTeam())
                .profileImageUrl(member.getProfileImage())
                .level(member.getMemberLevel() != null ? member.getMemberLevel().getLevel() :0)
                .experience(member.getMemberLevel() != null ? member.getMemberLevel().getExp() : 0)
                .nextLevelExp(member.getMemberLevel() != null ? member.getMemberLevel().getNextLevelExp() : 0)
                .postList(posts)
               .build();
    }

    // member 프로필 수정
    @Transactional
    public void updateMemberProfile(Long memberId, MemberProfileReqDto updateDto){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("Member not found with ID: " + memberId));

        member.updateProfile(updateDto);

        memberRepository.save(member);
    }
}
