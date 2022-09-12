package org.liubility.typing.server.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

/**
 * @Author: JDragon
 * @Data:2022/9/11 19:43
 * @Description:
 */

@Data
public class WordLibDTO {

    @ApiModelProperty(value = "词库名称", required = true)
    @NotBlank(message = "词库名称不能为空")
    private String wordLibName;

    @ApiModelProperty(value = "几码上屏方案（最长码长）", example = "4", required = true)
    @NotBlank(message = "最长码长不能为空")
    private Integer codeMaxLength;

    @ApiModelProperty(value = "选重符号", example = "23456789", required = true)
    @NotBlank(message = "选重符号不能为空")
    private String duplicateSymbols;

//    @ApiModelProperty(value = "选重权重", example = "0.5", required = true)
//    @NotBlank(message = "选重权重不能为空")
//    private Double duplicateSymbolWeight;

//    @ApiModelProperty(value = "码长权重", example = "0.5", required = true)
//    @NotBlank(message = "码长权重不能为空")
//    private Double wordLengthWeight;

    @ApiModelProperty(value = "引导符号", example = ";'", required = true)
    @NotBlank(message = "引导符不能为空")
    private String leaderSymbols;

//    @ApiModelProperty(value = "键盘分区", example = "1qaz|2wsx|3edc|4rfv5tgb|6yhn|7ujm|8ik,|9ol.0p;/'[]-=\\", required = true)
//    @NotBlank(message = "键盘分区不能为空")
//    private String keyBoardPartition;

    @ApiModelProperty(value = "码表文件")
    private MultipartFile file;
}
