package com.khu.yaong.domain.member.domain;

import com.khu.yaong.domain.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLevel extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    private int exp;

    @Column(name = "next_level_exp")
    private Integer nextLevelExp;

    public MemberLevel(Member member, int level, int exp, Integer nextLevelExp) {
        this.member = member;
        this.level = level;
        this.exp = exp;
        this.nextLevelExp = nextLevelExp;
    }

}
