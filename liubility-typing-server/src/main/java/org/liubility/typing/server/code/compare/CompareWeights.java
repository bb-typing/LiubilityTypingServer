package org.liubility.typing.server.code.compare;

import org.liubility.typing.server.code.parse.SubscriptInstance;

/**
 * @Author: JDragon
 * @Data:2022/9/5 17:01
 * @Description:
 */
public abstract class CompareWeights {
    public abstract double compare(SubscriptInstance[] subscriptInstances, Integer index, Integer preIndex, String code);
}
