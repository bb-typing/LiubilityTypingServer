package org.liubility.typing.server.enums.exception;

import lombok.Getter;
import org.liubility.commons.http.response.normal.ICode;

/**
 * @Author: JDragon
 * @Data:2022/9/13 0:39
 * @Description:
 */
public enum UserWordLibSettingCode implements ICode {
    /**
     * 找不到配置异常
     */
    NOT_FOUNT_SETTING(20700L,"找不到该配置"),
    ;

    @Getter
    private final Long code;

    @Getter
    private final String message;

    UserWordLibSettingCode(Long code, String message) {
        this.code = code;
        this.message = message;
    }
}
