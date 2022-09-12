package org.liubility.typing.server.domain.vo;

import lombok.Data;
import org.liubility.commons.dto.annotation.TableHeader;

/**
 * @Author: JDragon
 * @Data:2022/9/12 15:41
 * @Description:
 */

@Data
public class UserWordSettingListPageVO {

    @TableHeader("配置id")
    private Long id;

    private Long userId;

    @TableHeader("配置来源")
    private String username;

    private Long wordLibId;

    @TableHeader("词库名称")
    private String wordLibName;

    @TableHeader("键盘分区")
    private String keyBoardPartition;

    @TableHeader("选重权重")
    private Double duplicateSymbolWeight;

    @TableHeader("码长权重")
    private Double wordLengthWeight;

}
