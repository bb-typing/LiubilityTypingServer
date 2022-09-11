package org.liubility.typing.server.code.compare;

import org.apache.commons.lang3.StringUtils;
import org.liubility.typing.server.code.parse.SubscriptInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: JDragon
 * @Data:2022/9/5 17:02
 * @Description:
 */
public class CompareFeelDeviationWeights extends CompareWeights {

    private final Double duplicateSymbolWeights;

    private final Double wordLengthWeights;

    private final List<String> keyBoardPartitions = new ArrayList<>();

    protected final List<String> filterDuplicateSymbols;

    public CompareFeelDeviationWeights(Double duplicateSymbolWeights, Double wordLengthWeights, List<String> filterDuplicateSymbols) {
        this.duplicateSymbolWeights = duplicateSymbolWeights;
        this.wordLengthWeights = wordLengthWeights;
        this.filterDuplicateSymbols = filterDuplicateSymbols;
    }

    public void addKeyBoardPartition(String keyBoardPartition) {
        keyBoardPartitions.add(keyBoardPartition);
    }

    public void addKeyAllBoardPartition(List<String> keyBoardPartitionList) {
        keyBoardPartitions.addAll(keyBoardPartitionList);
    }

    @Override
    public double compare(SubscriptInstance[] subscriptInstances, Integer index, Integer preIndex, String word, String code) {
        SubscriptInstance subscriptInstance = subscriptInstances[index];
        //this
        double thisWeights = subscriptInstance.getWeights();

        //pre
        int preCodeLength = 0;
        double preFeelDeviation = 0;
        String preWordsCode = "";
        //上一跳的权重值
        double preWeights = 0;
        if (preIndex != -1) {
            SubscriptInstance preSubscriptInstance = subscriptInstances[preIndex];
            preWeights = preSubscriptInstance.getWeights();
            preCodeLength = preSubscriptInstance.getCodeLengthTemp();
            preFeelDeviation = preSubscriptInstance.getFeelDeviation();
            preWordsCode = preSubscriptInstance.getWordsCode();
        }

        //word
        int wordsCodeLength = code.length();
        String feelDeviationCode = code;
        if (StringUtils.isNoneBlank(preWordsCode)) {
            feelDeviationCode = preWordsCode.substring(preWordsCode.length() - 1) + feelDeviationCode;
        }
        double wordFeelDeviation = 0;
        int feelType = -1;
        List<String> feelDeviationCodeChars = feelDeviationCode.chars().mapToObj(c -> String.valueOf((char) c)).collect(Collectors.toList());
        for (String feelDeviationCodeChar : feelDeviationCodeChars) {
            for (int i = 0; i < keyBoardPartitions.size(); i++) {
                if (keyBoardPartitions.get(i).contains(feelDeviationCodeChar)) {
                    if (feelType == i) {
                        wordFeelDeviation++;
                    } else {
                        feelType = i;
                    }
                    break;
                }
            }
        }

        double wordsWeights = wordFeelDeviation;

        //选重涨权
        String duplicateSymbol = code.substring(wordsCodeLength);
        int duplicateNum = filterDuplicateSymbols.indexOf(duplicateSymbol) + 1;
        wordsWeights += wordsCodeLength - duplicateSymbolWeights * duplicateNum;

        //组词降权
        wordsWeights -= (word.length() - 1) * wordLengthWeights;

        //next
        double nextFeelDeviation = preFeelDeviation + wordFeelDeviation;
        int nextCodeLength = preCodeLength + wordsCodeLength;
        double nextWeights = preWeights + wordsWeights;
        /*
           权重比对
           原本的权重值 > 上一跳的权重值 + 该词编码的权重值时
           替换属性
         */
        if (thisWeights == 0 || thisWeights > nextWeights) {
            subscriptInstance.setWeights(nextWeights);
            subscriptInstance.setCodeLengthTemp(nextCodeLength);
            subscriptInstance.setFeelDeviation(nextFeelDeviation);
        }
        return nextWeights;
    }
}
