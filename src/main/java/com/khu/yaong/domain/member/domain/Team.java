package com.khu.yaong.domain.member.domain;

public enum Team {
    KIA,
    SAMSUNG,
    LG,
    DOOSAN,
    KT,
    KIWOOM,
    LOTTE,
    SSG,
    HANWHA,
    NC

    ;

    public static Team toEnum(String team) {
        return switch (team) {
            case "KIA" -> KIA;
            case "삼성" -> SAMSUNG;
            case "LG" -> LG;
            case "두산" -> DOOSAN;
            case "KT" -> KT;
            case "SSG" -> SSG;
            case "롯데" -> LOTTE;
            case "한화" -> HANWHA;
            case "NC" -> NC;
            case "키움" -> KIWOOM;
            default -> null;
        };
    }

}
