package org.liubility.typing.server.exception;

import lombok.Getter;
import org.liubility.commons.http.response.normal.ICode;

/**
 * @Author: JDragon
 * @Data:2022/9/12 3:10
 * @Description:
 */
public enum TypingMatchCode implements ICode {
    GET_AGAIN(60000L,"你今日已获取过赛文"),
    UPLOAD_FAIL(60001L,"上传成绩失败"),
    EXPIRED(60001L,"赛文已过期"),

    ;

    @Getter
    private final Long code;

    @Getter
    private final String message;

    TypingMatchCode(Long code, String message) {
        this.code = code;
        this.message = message;
    }
}