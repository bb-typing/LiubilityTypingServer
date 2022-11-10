package org.liubility.typing.server.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: JDragon
 * @Data:2022/10/3 23:23
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypedWordsDTO {

    private Long historyId;

    private List<Words> typeWords;

    private String typeCodes;


}
