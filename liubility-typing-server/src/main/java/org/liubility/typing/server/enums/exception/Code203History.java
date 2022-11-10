package org.liubility.typing.server.enums.exception;

import lombok.Getter;
import org.liubility.commons.http.response.normal.ICode;

/**
 * @Author: JDragon
 * @Data:2022/9/12 3:07
 * @Description:
 */
public enum Code203History implements ICode {
    ABNORMAL_GRADES(20300L, "成绩出现异常"),
    SAVE_ARTICLE_FAIL(20301L, "保存文档失败"),
    NOT_EXIST_ARTICLE(20302L, "未找到历史记录"),
    AUTH_FAIL_ARTICLE(20303L, "无法查询他人的历史记录"),
    NOT_EXIST_TYPED_WORDS(20304L, "未找到跟打详情"),
    ;

    @Getter
    private final Long code;

    @Getter
    private final String message;

    Code203History(Long code, String message) {
        this.code = code;
        this.message = message;
    }


}
