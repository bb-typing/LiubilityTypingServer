package org.liubility.typing.server.enums.exception;

import lombok.Getter;
import org.liubility.commons.http.response.normal.ICode;

/**
 * @Author: JDragon
 * @Data:2022/9/12 3:16
 * @Description:
 */
public enum WordLibCode implements ICode {
    UPLOAD_LIB_ERROR(20600L, "上传词库到对象存储异常"),
    INSERT_ERROR(20601L, "插入词库异常"),
    LOAD_LIB_ERROR(20602L, "加载词库异常"),
    NOT_SET_DEFAULT_WORD_LIB(20603L, "用户未设置默认词库"),
    NOT_FOUNT_DEFAULT_WORD_LIB(20604L, "未找到该默认词库"),
    NO_PERMISSION_DELETE(20605L, "权限不足"),
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