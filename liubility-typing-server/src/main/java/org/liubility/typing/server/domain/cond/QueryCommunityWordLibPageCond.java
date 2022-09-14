package org.liubility.typing.server.domain.cond;

import lombok.Data;
import org.liubility.typing.server.enums.OrderTypeEnum;
import org.liubility.typing.server.enums.WordLibOrderFieldEnum;

/**
 * @Author: JDragon
 * @Data:2022/9/14 0:25
 * @Description:
 */

@Data
public class QueryCommunityWordLibPageCond {

    private String name;

    private String author;

    private Integer maxCodeLength;

    private WordLibOrderFieldEnum orderBy;

    private OrderTypeEnum desc;
}
