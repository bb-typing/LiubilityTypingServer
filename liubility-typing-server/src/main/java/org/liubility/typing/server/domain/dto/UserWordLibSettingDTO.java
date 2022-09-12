package org.liubility.typing.server.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: JDragon
 * @Data:2022/9/12 14:39
 * @Description:
 */
@Data
public class UserWordLibSettingDTO {

    @ApiModelProperty("词库id，更新时需填")
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("词库id")
    private Long wordLibId;

    @ApiModelProperty("键盘分区")
    private String keyBoardPartition;

    @ApiModelProperty("选重权重")
    private Double duplicateSymbolWeight;

    @ApiModelProperty("码长权重")
    private Double wordLengthWeight;

    @ApiModelProperty("设为默认词提")
    private Boolean defaultLib;

}
