package com.khu.yaong.domain.data.service;

import com.khu.yaong.domain.data.domain.Pitcher;
import com.khu.yaong.domain.data.domain.Player;
import com.khu.yaong.domain.data.dto.PlayerReqDTO;
import com.khu.yaong.domain.data.dto.PlayerResDTO;
import com.khu.yaong.domain.data.repository.FielderRepository;
import com.khu.yaong.domain.data.repository.PitcherRepository;
import com.khu.yaong.domain.data.repository.PlayerRepository;
import com.khu.yaong.domain.member.domain.Team;
import com.khu.yaong.global.common.exception.BaseException;
import com.khu.yaong.global.common.response.ErrorCode;
import com.khu.yaong.global.common.response.data.DataErrorCode;
import com.khu.yaong.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PlayerServiceImpl implements PlayerService {

    private static final int REQUEST_THRESHOLD = 10;
    private static final int TIME_THRESHOLD = 0; //600000;

    private final PlayerRepository playerRepository;
    private final PitcherRepository pitcherRepository;
    private final FielderRepository fielderRepository;

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public List<PlayerResDTO.PlayerInfoDTO> getPlayers(Integer pageSize, Long cursorId, String cursorName, Team team, String query) {

        // Authorization
        SecurityUtil.getCurrentMemberId();

        return playerRepository.findBaseballPlayers(pageSize, cursorId, cursorName, team, query).stream()
                .map(PlayerResDTO.PlayerInfoDTO::toDTO)
                .toList();
    }

    @Override
    public PlayerResDTO.PlayerDetailDTO getPlayerInfo(Long playerId) {

        // Authorization
        Long memberId = SecurityUtil.getCurrentMemberId();

        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new BaseException(DataErrorCode.PLAYER_NOT_FOUND));

        return PlayerResDTO.PlayerDetailDTO.toDTO(player);
    }

    @Override
    public void requestPitcherMod(Long playerId, PlayerReqDTO.PitcherModDTO pitcherModDTO) {

        // Authorization
        Long memberId = SecurityUtil.getCurrentMemberId();

        PlayerReqDTO.PitcherModDTO.getAllFields()
                .forEach(field -> {
                    // 수정 요청한 값 저장
                    String value;
                    try {
                        field.setAccessible(true);
                        Object objValue = field.get(pitcherModDTO);
                        value = objValue != null ? objValue.toString() : "";
                    } catch (IllegalAccessException e) {
                        throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR);
                    }

                    // null인 필드에 대해서는 수정 요청하지 않음
                    if (value == null || value.isEmpty()) {
                        return ;
                    }

                    // Redis Key
                    String reqTimeKey = playerId + ":" + field.getName() + ":" + memberId + ":time";    // 특정 회원의 최근 수정 요청 시각을 선수 기록별로 저장하는 키
                    String reqCountKey = playerId + ":" + field.getName() + ":" + value + ":count" ;    // 선수 기록별 동일 요청 횟수를 저장하는 키
                    String reqValueKey = playerId + ":" + field.getName() + ":" + value;                // 선수 기록별 수정 요청 내용을 저장하는 키

                    // 중복 요청 방지
                    // 같은 필드에 대해 같은 회원이 일정 기간 내에 여러 번 요청할 수 없음
                    String lastRequestTime = redisTemplate.opsForValue().get(reqTimeKey);
                    if (lastRequestTime != null) {

                        long elapsedTime = System.currentTimeMillis() - Long.parseLong(lastRequestTime);
                        if (elapsedTime < TIME_THRESHOLD) {
                            return;
                        }
                    }

                    // 선수 기록별 요청 횟수 확인
                    String stringCnt = redisTemplate.opsForValue().get(reqCountKey);
                    int reqCnt = stringCnt != null ? Integer.parseInt(stringCnt) : 0;

                    // 요청 횟수가 10번 이상이면 DB에 반영
                    if (reqCnt >= REQUEST_THRESHOLD) {
                        // 수정사항 저장
                        savePitcherModRequest(playerId, field, value);

                        // 요청 횟수 초기화 & 기준 시각 초기화
                        redisTemplate.delete(reqCountKey);
                        redisTemplate.delete(reqTimeKey);

                    } else {
                        // 요청 횟수 증가 & 요청 시각 기록
                        redisTemplate.opsForValue().increment(reqCountKey, 1);
                        redisTemplate.opsForValue().set(reqTimeKey, String.valueOf(System.currentTimeMillis()));
                    }
                });
    }

    private void savePitcherModRequest(Long playerId, Field field, String value) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new BaseException(DataErrorCode.PLAYER_NOT_FOUND));
        Pitcher pitcher = pitcherRepository.findByPlayerId(playerId)
                .orElseThrow(() -> new BaseException(DataErrorCode.PLAYER_NOT_FOUND));

        pitcher.setData(field, value);
        player.setPitcher(pitcher);
        pitcherRepository.save(pitcher);
    }
}
