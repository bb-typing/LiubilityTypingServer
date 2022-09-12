package org.liubility.typing.server.enums.exception;

import lombok.Getter;
import org.liubility.commons.http.response.normal.ICode;

/**
 * @Author: JDragon
 * @Data:2022/9/12 3:00
 * @Description:
 */
public enum AccountCode implements ICode {
    USER_EXIST(20100L,"该用户名已存在"),

    ;

    @Getter
    private final Long code;

    @Getter
    private final String message;

    AccountCode(Long code, String message) {
        this.code = code;
        this.message = message;
    }


}
