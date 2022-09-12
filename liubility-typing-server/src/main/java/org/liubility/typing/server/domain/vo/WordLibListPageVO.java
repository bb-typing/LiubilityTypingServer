package org.liubility.typing.server.domain.vo;

import lombok.Data;
import org.liubility.commons.dto.annotation.TableHeader;

/**
 * @Author: JDragon
 * @Data:2022/9/12 13:35
 * @Description:
 */
@Data
public class WordLibListPageVO {

    @TableHeader("词库ID")
    private Long id;

    private Long userId;

    @TableHeader("词库来源")
    private String username;

    @TableHeader("词条数")
    private Integer wordCount;

    @TableHeader("最长词长")
    private Integer wordMaxLength;

    @TableHeader("最长编码")
    private Integer codeMaxLength;

    @TableHeader("选重符")
    private String duplicateSymbols;

}
