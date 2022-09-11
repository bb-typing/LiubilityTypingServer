package org.liubility.typing.server.exception;

import lombok.Getter;
import org.liubility.commons.http.response.normal.ICode;

/**
 * @Author: JDragon
 * @Data:2022/9/12 3:07
 * @Description:
 */
public enum HistoryCode implements ICode {
    ABNORMAL_GRADES(50000L,"成绩出现异常"),
    SAVE_ARTICLE_FAIL(50001L,"保存文档失败")
    ;

    @Getter
    private final Long code;

    @Getter
    private final String message;

    HistoryCode(Long code, String message) {
        this.code = code;
        this.message = message;
    }


}
