package org.liubility.typing.server.enums.exception;

import lombok.Getter;
import org.liubility.commons.http.response.normal.ICode;

/**
 * @Author: JDragon
 * @Data:2022/10/2 20:53
 * @Description:
 */
public enum Code208TypingSetting implements ICode {
    VALID_JSON_FAIL(20800L, "配置json校验失败")
    ;
    @Getter
    private final Long code;

    @Getter
    private final String message;

    Code208TypingSetting(Long code, String message) {
        this.code = code;
        this.message = message;
    }
}
