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
        return Player.builder()
                .name(name)
                .no(toInteger(no))
                .position(position)
                .birth(LocalDate.parse(birth))
                .hwSpec(hw)
                .build();
    }

    public Pitcher toPitcher(Player player) {
        return Pitcher.builder()
                .era(toDouble(era))
                .win(toInteger(w))
                .lose(toInteger(l))
                .sv(toInteger(sv))
                .hld(toInteger(hld))
                .ip(ip)
                .so(toInteger(so))
                .ha(toInteger(ha))
                .hra(toInteger(hr))
                .player(player)
                .build();
    }

    public Fielder toFielder(Player player) {
        return Fielder.builder()
                .avg(toDouble(avg))
                .hr(toInteger(hr))
                .h(toInteger(h))
                .r(toInteger(r))
                .rbi(toInteger(rbi))
                .sb(toInteger(sb))
                .obp(toDouble(obp))
                .ops(toDouble(ops))
                .player(player)
                .build();
    }

    private Integer toInteger(String value) {
        try {
            return value != null ? Integer.valueOf(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Double toDouble(String value) {
        try {
            return value != null ? Double.valueOf(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
