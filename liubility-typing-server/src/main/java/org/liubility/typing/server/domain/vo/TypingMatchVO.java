package org.liubility.typing.server.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.liubility.typing.server.domain.entity.Article;

import java.util.Date;

/**
 * @Author JDragon
 * @Date 2021.08.29 下午 10:41
 * @Email 1061917196@qq.com
 * @Des:
 */

@Data
public class TypingMatchVO {

    private Integer id;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date holdDate;

    private Integer articleId;

    private String author;

    private Integer matchType;

    private Article article;
}
