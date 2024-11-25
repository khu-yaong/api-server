package com.khu.yaong.domain.data.dto;

public enum Team {

    KIA, SS, LG, DS, KT, SSG, LT, HH, NC, KW
    ;

    public static Team toEnum(String team) {
        return switch (team) {
            case "KIA" -> KIA;
            case "삼성" -> SS;
            case "LG" -> LG;
            case "두산" -> DS;
            case "KT" -> KT;
            case "SSG" -> SSG;
            case "롯데" -> LT;
            case "한화" -> HH;
            case "NC" -> NC;
            case "키움" -> KW;
            default -> null;
        };
    }
}
