package org.liubility.typing.server.enums;

import org.liubility.commons.interceptor.translation.IDictEnum;

/**
 * <p></p>
 * <p>create time: 2021/12/26 23:29 </p>
 *
 * @author : Jdragon
 */
public enum MobileEnum implements IDictEnum<Boolean, String> {
    mobile(true, "手机端"),
    pc(false, "PC端");
    MobileEnum(Boolean code, String value) {
        this.code = code;
        this.value = value;
    }

    private final Boolean code;

    private final String value;

    @Override
    public Boolean getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return value;
    }
}
