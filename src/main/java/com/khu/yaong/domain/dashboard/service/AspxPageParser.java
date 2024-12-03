package com.khu.yaong.domain.dashboard.service;

import com.khu.yaong.domain.dashboard.dto.*;
import com.khu.yaong.domain.dashboard.exception.CrawlingErrorCode;
import com.khu.yaong.domain.dashboard.exception.CrawlingException;
import com.khu.yaong.global.common.exception.BaseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class AspxPageParser {

    public GameInfoDto parseHtmlToJson(String htmlContent) {
        if (htmlContent == null || htmlContent.isEmpty()) {
            throw new CrawlingException(CrawlingErrorCode.PARSE_FAILED);
        }
        Document doc = Jsoup.parse(htmlContent);

        // 1. 팀 이름 및 점수 추출
        String date = getText(doc.selectFirst(".present .date"));
        String inning = getText(doc.selectFirst(".present .base strong:nth-of-type(1)"));
        Elements strongElements = doc.select(".present .base strong"); // 체크
        String scoreStatus = strongElements.size() > 1 ? strongElements.get(1).text() : "";
        String groundInfo = getText(doc.selectFirst(".ground"));


        // 2. 팀 정보
        TeamDto awayTeam = extractTeamInfo(doc, ".awayBox");
        TeamDto homeTeam = extractTeamInfo(doc, ".homeBox");
        TeamInfoDto teams = new TeamInfoDto(awayTeam, homeTeam);

        // 3. 현재 플레이 정보

        /*// 베이스 상태 : 각 루의 주자 상태 이미지
        Element baseStatusElement = doc.selectFirst("#imgThisGameBase[src]");
        currentPlay.put("baseStatus", baseStatusElement != null ? baseStatusElement.attr("src") : "");
        result.put("currentPlay", currentPlay);*/

        // 베이스 상태 : 각 루의 주자 상태
        Map<String, String> baseRunners = new HashMap<>();
        Element firstBaseRunner = doc.selectFirst(".playerName .typing1");
        Element secondBaseRunner = doc.selectFirst(".playerName .typing2");
        Element thirdBaseRunner = doc.selectFirst(".playerName .typing3");
        baseRunners.put("1Base", firstBaseRunner != null ? firstBaseRunner.text() : "None");
        baseRunners.put("2Base", secondBaseRunner != null ? secondBaseRunner.text() : "None");
        baseRunners.put("3Base", thirdBaseRunner != null ? thirdBaseRunner.text() : "None");

        CurrentPlayDto currentPlay = new CurrentPlayDto(
                getText(doc.selectFirst(".playerName .supervision2")),
                getText(doc.selectFirst(".playerName .pitcher")),
                getText(doc.selectFirst(".playerName .catcher")),
                doc.select(".sbo .b ul li.on").size(),
                doc.select(".sbo .s ul li.on").size(),
                doc.select(".sbo .o ul li.on").size(),
                baseRunners
        );


        // 4. 중계정보 추출

        // 각 이닝별 점수 추출
        List<List<String>> inningScores = new ArrayList<>();
        Elements rows = doc.select("#tblScoreBoard2 tbody tr");
        for (Element row : rows) {
            List<String> innings = new ArrayList<>();
            Elements cells = row.select("td");
            for (Element cell : cells) {
                innings.add(cell.text());
            }
            inningScores.add(innings);
        }

        // 총 득점, 안타, 실책, 볼넷
        List<Map<String, String>> summaryStats = new ArrayList<>();
        Elements statsRows = doc.select("#tblScoreBoard3 tbody tr");
        for (Element row : statsRows) {
            Map<String, String> stats = new HashMap<>();
            Elements cells = row.select("td");
            if (cells.size() >= 4) {
                stats.put("R", cells.get(0).text());
                stats.put("H", cells.get(1).text());
                stats.put("E", cells.get(2).text());
                stats.put("B", cells.get(3).text());
            }
            summaryStats.add(stats);
        }

        LiveBroadcastDto liveBroadcast = new LiveBroadcastDto(
                getText(doc.selectFirst(".teamAway .txt")),
                getText(doc.selectFirst(".teamAway em")),
                getText(doc.selectFirst(".teamHome .txt")),
                getText(doc.selectFirst(".teamHome em")),
                inningScores,
                summaryStats
        );

        // 5. 타구장 소식 정보
        List<OtherGameDto> otherGames = doc.select(".otherGame .items table").stream()
                .map(table -> new OtherGameDto(
                        getText(table.selectFirst("a")),
                        getText(table.selectFirst("tr:nth-of-type(1) td:nth-of-type(2)")),
                        getText(table.selectFirst("tr:nth-of-type(1) td:nth-of-type(3) em")),
                        getText(table.selectFirst("tr:nth-of-type(2) td:nth-of-type(1)")),
                        getText(table.selectFirst("tr:nth-of-type(2) td:nth-of-type(2) em")),
                        table.selectFirst(".groundBase img").attr("alt"),
                        getText(table.selectFirst(".groundBase span"))
                )).collect(Collectors.toList());



        return new GameInfoDto(date, inning, scoreStatus, groundInfo, teams, currentPlay, liveBroadcast, otherGames);

    }

    private static String getText(Element element) {
        return element != null ? element.text() : "";
    }

    private static TeamDto extractTeamInfo(Document doc, String teamSelector) {
        Map<String, Object> teamInfo = new HashMap<>();

        // 팀 이름과 엠블럼
        Element teamNameElement = doc.selectFirst(teamSelector + " .who");
        Element teamEmblemElement = doc.selectFirst(teamSelector + " .player-img img.team");
        teamInfo.put("teamName", teamNameElement != null ? teamNameElement.ownText() : "");
        teamInfo.put("teamEmblem", teamEmblemElement != null ? teamEmblemElement.attr("src") : "");

        // 선수 성적 -타자
        List<Map<String, String>> batters = new ArrayList<>();
        Elements batterRows = doc.select(teamSelector + " .boxscore .tList:not(.tList2) tbody tr");
        for (Element row : batterRows) {
            Map<String, String> player = new HashMap<>();
            List<Element> cols = row.select("td");
            player.put("position", cols.get(0).text());
            player.put("name", cols.get(1).text());
            player.put("ab", cols.get(2).text()); // 타수
            player.put("runs", cols.get(3).text()); // 득점
            player.put("hits", cols.get(4).text()); // 안타
            batters.add(player);
        }

        // 선수 성적 - 투수
        List<Map<String, String>> pitchers = new ArrayList<>();
        Elements pitcherRows = doc.select(teamSelector + " .boxscore .tList2 tbody tr");
        for (Element row : pitcherRows) {
            Map<String, String> player = new HashMap<>();
            List<Element> cols = row.select("td");
            player.put("name", cols.get(0).text());
            player.put("innings", cols.get(1).text());
            player.put("hits", cols.get(2).text());
            player.put("strikeOuts", cols.get(3).text());
            player.put("earnedRuns", cols.get(4).text());
            pitchers.add(player);
        }

        return new TeamDto(
                teamInfo,
                batters,
                pitchers
        );

    }
}
