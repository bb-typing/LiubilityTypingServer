package org.liubility.typing.server.enums.exception;

import lombok.Getter;
import org.liubility.commons.http.response.normal.ICode;

/**
 * @Author: JDragon
 * @Data:2022/9/12 3:14
 * @Description:
 */
public enum Code205UserTyping implements ICode {
    NOT_EXIST(20500L,"你还未申请跟打器账号"),
    SECRET_ERROR(20501L,"密钥错误"),

    ;

    @Getter
    private final Long code;

    @Getter
    private final String message;

    Code205UserTyping(Long code, String message) {
        this.code = code;
        this.message = message;
    }
}