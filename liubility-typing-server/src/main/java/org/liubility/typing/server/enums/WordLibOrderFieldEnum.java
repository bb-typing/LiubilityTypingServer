package org.liubility.typing.server.enums;

import lombok.Getter;

/**
 * @Author: JDragon
 * @Data:2022/9/15 1:20
 * @Description:
 */

public enum WordLibOrderFieldEnum {

    CODE_MAX_LENGTH("code_max_length"),
    CREATE_TIME("create_time"),
    SHARE_TIME("share_time");
    ;

    @Getter
    private final String fieldName;

    WordLibOrderFieldEnum(String fieldName) {
        this.fieldName = fieldName;
    }
}
