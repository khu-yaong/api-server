package com.khu.yaong.domain.data.dto;

import com.khu.yaong.domain.data.domain.Fielder;
import com.khu.yaong.domain.data.domain.Pitcher;
import com.khu.yaong.domain.data.domain.Player;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class PlayerDTO {

    private final String avg;
    private final String bb;
    private final String birth;
    private final String era;
    private final String h;
    private final String ha;
    private final String hld;
    private final String hr;
    private final String hw;
    private final String ip;
    private final String l;
    private final String name;
    private final String no;
    private final String obp;
    private final String ops;
    private final String position;
    private final String r;
    private final String rbi;
    private final String sb;
    private final String so;
    private final String sv;
    private final String team;
    private final String w;
    private final String whip;

    public Player toPlayer() {
        try {
            return Player.builder()
                    .name(name)
                    .no(Integer.valueOf(no))
                    .position(position)
                    .birth(LocalDate.parse(birth))
                    .hwSpec(hw)
                    .build();
        } catch(NumberFormatException e) {
            return null;
        }
    }

    public Pitcher toPitcher(Player player) {
        try {
            return Pitcher.builder()
                    .era(Double.valueOf(era))
                    .win(Integer.valueOf(w))
                    .lose(Integer.valueOf(l))
                    .sv(Integer.valueOf(sv))
                    .hld(Integer.valueOf(hld))
                    .ip(ip)
                    .so(Integer.valueOf(so))
                    .ha(Integer.valueOf(ha))
                    .hra(Integer.valueOf(hr))
                    .player(player)
                    .build();
        } catch(NumberFormatException e) {
            return null;
        }
    }

    public Fielder toFielder(Player player) {
        try {
            return Fielder.builder()
                    .avg(Double.valueOf(avg))
                    .hr(Integer.valueOf(hr))
                    .h(Integer.valueOf(h))
                    .r(Integer.valueOf(r))
                    .rbi(Integer.valueOf(rbi))
                    .sb(Integer.valueOf(sb))
                    .obp(Double.valueOf(obp))
                    .ops(Double.valueOf(ops))
                    .player(player)
                    .build();
        } catch(NumberFormatException e) {
            return null;
        }
    }

}
