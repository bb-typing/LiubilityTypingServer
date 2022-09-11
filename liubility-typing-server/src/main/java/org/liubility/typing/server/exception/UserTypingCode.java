package org.liubility.typing.server.exception;

import lombok.Getter;
import org.liubility.commons.http.response.normal.ICode;

/**
 * @Author: JDragon
 * @Data:2022/9/12 3:14
 * @Description:
 */
public enum UserTypingCode implements ICode {
    NOT_EXIST(70000L,"你还未申请跟打器账号"),
    SECRET_ERROR(70000L,"密钥错误"),

    ;

    @Getter
    private final Long code;

    @Getter
    private final String message;

    UserTypingCode(Long code, String message) {
        this.code = code;
        this.message = message;
    }
}