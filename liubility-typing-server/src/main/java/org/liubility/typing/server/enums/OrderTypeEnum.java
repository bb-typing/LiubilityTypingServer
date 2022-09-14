package org.liubility.typing.server.enums;

import lombok.Getter;

/**
 * @Author: JDragon
 * @Data:2022/9/15 2:01
 * @Description:
 */
public enum OrderTypeEnum {
    DESC("desc"),
    ASC("asc"),
    ;

    @Getter
    private final String orderType;

    OrderTypeEnum(String orderType) {
        this.orderType = orderType;
    }
}
