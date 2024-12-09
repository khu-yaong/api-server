package com.khu.yaong.domain.data.domain;

import com.khu.yaong.domain.member.domain.Team;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 5)
    private Team team;

    @Column(length = 10)
    private String name;

    @Column(length = 4)
    private Integer no;

    @Column(length = 10)
    private String position;

    @Column(length = 12)
    private LocalDate birth;

    @Column(length = 30)
    private String hwSpec;

    @Column
    private String profile;

    @Setter
    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private Pitcher pitcher = null;

    @Setter
    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private Fielder fielder = null;
}
