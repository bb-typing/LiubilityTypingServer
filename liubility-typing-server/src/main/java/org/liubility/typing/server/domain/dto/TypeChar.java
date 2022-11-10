package org.liubility.typing.server.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: JDragon
 * @Data:2022/11/10 22:54
 * @Description:
 */
@Data
@AllArgsConstructor
public class TypeChar {

    private Character character;

    private Long typingTime;

    private Long deleteTime;

    private Boolean mistake;
}
