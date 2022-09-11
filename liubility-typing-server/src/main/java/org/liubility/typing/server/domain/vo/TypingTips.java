package org.liubility.typing.server.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.liubility.typing.server.code.parse.SubscriptInstance;

/**
 * @Author: JDragon
 * @Data:2022/9/12 3:22
 * @Description:
 */
@Data
@AllArgsConstructor
public class TypingTips {

    @ApiModelProperty(value = "词语提示")
    private SubscriptInstance[] subscriptInstances;

    @ApiModelProperty(value = "理论编码")
    private String codes;


}
