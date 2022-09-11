package org.liubility.typing.server.exception;

import lombok.Getter;
import org.liubility.commons.http.response.normal.ICode;

/**
 * @Author: JDragon
 * @Data:2022/9/12 3:16
 * @Description:
 */
public enum WordLibCode implements ICode {
    UPLOAD_LIB_ERROR(80000L,"上传词库到对象存储异常"),
    INSERT_ERROR(80001L,"插入词库异常"),
    LOAD_LIB_ERROR(80002L,"加载词库异常"),
    ;

    @Getter
    private final Long code;

    @Getter
    private final String message;

    WordLibCode(Long code, String message) {
        this.code = code;
        this.message = message;
    }
}