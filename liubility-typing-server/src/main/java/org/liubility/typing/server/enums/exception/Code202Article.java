package org.liubility.typing.server.enums.exception;

import lombok.Getter;
import org.liubility.commons.http.response.normal.ICode;

/**
 * @Author: JDragon
 * @Data:2022/9/12 3:05
 * @Description:
 */
public enum Code202Article implements ICode {
    NOT_EXIST_ARTICLE(20200L,"没有该文章")
    ;

    @Getter
    private final Long code;

    @Getter
    private final String message;

    Code202Article(Long code, String message) {
        this.code = code;
        this.message = message;
    }


}
