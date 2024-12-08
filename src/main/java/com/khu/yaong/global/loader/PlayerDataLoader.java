package com.khu.yaong.global.loader;

import com.khu.yaong.domain.data.domain.Fielder;
import com.khu.yaong.domain.data.domain.Pitcher;
import com.khu.yaong.domain.data.domain.Player;
import com.khu.yaong.domain.data.dto.PlayerDTO;
import com.khu.yaong.domain.data.repository.FielderRepository;
import com.khu.yaong.domain.data.repository.PitcherRepository;
import com.khu.yaong.domain.data.repository.PlayerRepository;
import com.khu.yaong.global.common.exception.BaseException;
import com.khu.yaong.global.common.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;

@Component
@RequiredArgsConstructor
public class PlayerDataLoader implements CommandLineRunner {

    private final PlayerRepository playerRepository;
    private final PitcherRepository pitcherRepository;
    private final FielderRepository fielderRepository;

    @Override
    public void run(String... args) {
        // 선수 데이터가 db에 저장되어 있지 않으면 csv 파일에서 로드
        String path = "data/player_raw_data.csv";
        if (playerRepository.count() == 0) {
            savePlayerData(path);
        }
    }

    private void savePlayerData(String filePath) {

        try (InputStreamReader reader = new InputStreamReader(new ClassPathResource(filePath).getInputStream())) {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim());

            for (final CSVRecord csvRecord : csvParser) {
                PlayerDTO playerDTO = PlayerDTO.builder()
                        .profile(csvRecord.get("profile"))
                        .avg(csvRecord.get("avg"))
                        .bb(csvRecord.get("bb"))
                        .birth(csvRecord.get("birth"))
                        .era(csvRecord.get("era"))
                        .h(csvRecord.get("h"))
                        .ha(csvRecord.get("ha"))
                        .hld(csvRecord.get("hld"))
                        .hr(csvRecord.get("hr"))
                        .hw(csvRecord.get("hw"))
                        .ip(csvRecord.get("ip"))
                        .l(csvRecord.get("l"))
                        .name(csvRecord.get("name"))
                        .no(csvRecord.get("no"))
                        .obp(csvRecord.get("obp"))
                        .ops(csvRecord.get("ops"))
                        .position(csvRecord.get("position"))
                        .r(csvRecord.get("r"))
                        .rbi(csvRecord.get("rbi"))
                        .sb(csvRecord.get("sb"))
                        .so(csvRecord.get("so"))
                        .sv(csvRecord.get("sv"))
                        .team(csvRecord.get("team"))
                        .w(csvRecord.get("w"))
                        .whip(csvRecord.get("whip"))
                        .build();

                Player player = playerDTO.toPlayer();
                Player savedPlayer = playerRepository.save(player);
                if (player.getPosition().equals("투수")) {
                    Pitcher pitcher = playerDTO.toPitcher(savedPlayer);
                    pitcherRepository.save(pitcher);
                    savedPlayer.setPitcher(pitcher);
                } else {
                    Fielder fielder = playerDTO.toFielder(savedPlayer);
                    fielderRepository.save(fielder);
                    savedPlayer.setFielder(fielder);
                }
            }
        } catch (IOException e) {
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}
