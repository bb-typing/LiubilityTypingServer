package org.liubility.typing.server.exception;

import lombok.Getter;
import org.liubility.commons.http.response.normal.ICode;

/**
 * @Author: JDragon
 * @Data:2022/9/12 3:05
 * @Description:
 */
public enum ArticleCode implements ICode {
    NOT_EXIST_ARTICLE(40000L,"没有该文章")
    ;

    @Getter
    private final Long code;

    @Getter
    private final String message;

    ArticleCode(Long code, String message) {
        this.code = code;
        this.message = message;
    }


}
