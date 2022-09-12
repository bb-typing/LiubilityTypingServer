package org.liubility.typing.server.code.compare;

import org.liubility.typing.server.code.parse.SubscriptInstance;

/**
 * @Author: JDragon
 * @Data:2022/9/5 17:01
 * @Description:
 */
public abstract class CompareWeights {
    /**
     * 词提dp时路径对比权重
     *
     * @param subscriptInstances 文章
     * @param index              当前下标
     * @param preIndex           上一跳下标
     * @param word               计算词组
     * @param code               计算词组编码
     * @return 权重值
     */
    public abstract double compare(SubscriptInstance[] subscriptInstances, Integer index, Integer preIndex, String word, String code);
}
