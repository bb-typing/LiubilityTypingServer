package org.liubility.typing.server.code.compare;

import org.liubility.typing.server.code.parse.SubscriptInstance;

/**
 * @Author: JDragon
 * @Data:2022/9/5 17:02
 * @Description:
 */
public class CompareCodeLengthWeights extends CompareWeights {

    /**
     * 计算权重，是否替换当前下标的权重值
     */
    @Override
    public double compare(SubscriptInstance[] subscriptInstances, Integer index, Integer preIndex, String word, String code) {
        SubscriptInstance subscriptInstance = subscriptInstances[index];

        double thisWeights = subscriptInstance.getWeights();
        //上一跳的权重值
        double preWeights = 0;
        if (preIndex != -1) {
            SubscriptInstance preSubscriptInstance = subscriptInstances[preIndex];
            preWeights = preSubscriptInstance.getWeights();
        }
        int wordsCodeLength = code.length();
        double wordsWeights = wordsCodeLength * 1.0;
        double nextWeights = preWeights + wordsWeights;
        /*
           权重比对
           原本的权重值 > 上一跳的权重值 + 该词编码的权重值时
           替换属性
         */
        if (thisWeights == 0 || thisWeights > nextWeights) {
            subscriptInstance.setWeights(nextWeights);
        }
        return nextWeights;
    }
}
