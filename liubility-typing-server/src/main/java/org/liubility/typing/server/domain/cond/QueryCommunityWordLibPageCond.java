package org.liubility.typing.server.domain.cond;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @Author: JDragon
 * @Data:2022/9/14 0:25
 * @Description:
 */

@Data
public class QueryCommunityWordLibPageCond {

    private String author;

    private Integer maxCodeLength;



}
