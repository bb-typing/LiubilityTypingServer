package org.liubility.typing.server.enums.exception;

import lombok.Getter;
import org.liubility.commons.http.response.normal.ICode;

/**
 * @Author: JDragon
 * @Data:2022/9/12 3:10
 * @Description:
 */
public enum Code204TypingMatch implements ICode {
    GET_AGAIN(20400L,"你今日已获取过赛文"),
    UPLOAD_FAIL(20401L,"上传成绩失败"),
    EXPIRED(20402L,"赛文已过期"),

    ;

    @Getter
    private final Long code;

    @Getter
    private final String message;

    Code204TypingMatch(Long code, String message) {
        this.code = code;
        this.message = message;
    }
}