package org.liubility.typing.server.compare;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: JDragon
 * @Data:2022/9/3 17:37
 * @Description:
 */

@Data
@AllArgsConstructor
public class ComparisonItem {

    private String word;

    private Integer type;
}