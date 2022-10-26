package org.liubility.typing.server.enums;

import lombok.Getter;

/**
 * @Author: JDragon
 * @Data:2022/10/25 21:52
 * @Description:
 */
public enum TypingWordsTypeEnum {
    RIGHT("1", "全等"),
    /**
     * 词组拆分正确
     */
    BREAK("2", "短"),
    /**
     * 词组延伸正确
     */
    EXT("3", "长"),

    ;

    @Getter
    private final String code;

    @Getter
    private final String desc;

    TypingWordsTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TypingWordsTypeEnum compare(String word, String tips) {
        if (word.length() == tips.length()) {
            return RIGHT;
        } else if (word.length() < tips.length()) {
            return BREAK;
        } else {
            return EXT;
        }
    }
}
