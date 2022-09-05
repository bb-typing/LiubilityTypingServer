package org.liubility.typing.server.code.parse;

import lombok.extern.slf4j.Slf4j;
import org.liubility.typing.server.code.convert.WordTypeConvert;
import org.liubility.typing.server.code.libs.TrieWordLib;
import org.liubility.typing.server.code.parse.SubscriptInstance.PreInfo;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Author: JDragon
 * @Data:2022/9/2 9:32
 * @Description:
 */
@Slf4j
public class TrieWordParser {

    private final TrieWordLib wordLib;

    private final TrieWordLib symbolLib;

    private final WordTypeConvert typeConvert;

    public TrieWordParser(TrieWordLib wordLib, TrieWordLib symbolLib, WordTypeConvert typeConvert) {
        this.wordLib = wordLib;
        this.symbolLib = symbolLib;
        this.typeConvert = typeConvert;
    }

    public SubscriptInstance[] parse(String article) {
        try {

            SubscriptInstance[] subscriptInstances = createSubscriptInstance(article);

            this.fillInWords(subscriptInstances);

            this.reverseDp(subscriptInstances);

            return subscriptInstances;
        } catch (Exception e) {
            log.error("计算词提出错", e);
            return null;
        }
    }

    /**
     * 删除顶屏符
     *
     * @param codeTemp           计算顶屏的编码
     * @param subscriptInstances 文章
     * @param cursor             计算下标
     */
    public String deleteDefaultUpSymbol(String codeTemp, SubscriptInstance[] subscriptInstances, Integer cursor) {
        int maxIndex = subscriptInstances.length - 1;
        //codeTemp最后一位是否为上屏符 &&
        //codeTemp后一位是否是顶屏符号 &&
        //codeTemp后是否不为顶屏符号为首的词
        if (maxIndex >= cursor + 1
                && codeTemp.endsWith(wordLib.getDefaultUpSymbol())
                && symbolLib.getCode(subscriptInstances[cursor + 1].getWord()) != null
                && (maxIndex < cursor + 2 ||
                wordLib.getNode(subscriptInstances[cursor + 1].getWord() + subscriptInstances[cursor + 2].getWord()) == null)
        ) {
            codeTemp = codeTemp.substring(0, codeTemp.length() - 1);
        }
        return codeTemp;
    }

    /**
     * 创建article长度的SubscriptInstance数组
     * 并对每个SubscriptInstance进行初始化
     * 判断该下标字符是否在单字码表中，如果无，则判断是否为数组或字母，是则直接设置codeTemp为字符自身
     * 构造函数创建实例。详见SubscriptInstance构造方法
     */
    public SubscriptInstance[] createSubscriptInstance(String article) {
        SubscriptInstance[] subscriptInstances = new SubscriptInstance[article.length()];
        int articleLength = article.length();
        String codeTemp, strTemp;

        for (int index = articleLength - 1; index >= 0; index--) {
            strTemp = article.substring(index, index + 1);
            char charTemp = strTemp.toCharArray()[0];
            codeTemp = wordLib.getCode(strTemp);
            if (codeTemp == null) {
                codeTemp = symbolLib.getCode(strTemp);
                if (codeTemp == null) {
                    codeTemp = strTemp + "?";
                } else if ((charTemp >= 'a' && charTemp <= 'z')
                        || (charTemp >= 'A' && charTemp <= 'Z')
                        || (charTemp >= '0' && charTemp <= '9')) {
                    codeTemp = strTemp;
                }
            } else {
                codeTemp = deleteDefaultUpSymbol(codeTemp, subscriptInstances, index);
            }
            SubscriptInstance subscriptInstance = new SubscriptInstance(index, strTemp, codeTemp);

            subscriptInstances[index] = subscriptInstance;
        }
        return subscriptInstances;
    }

    /**
     * 正向遍历进行词语填充与计算码长
     */
    public void fillInWords(SubscriptInstance[] subscriptInstances) {
        String codeTemp, strTemp;
        int articleLength = subscriptInstances.length;
        for (int index = 0; index < articleLength; index++) {
            int articleMaxIndex = articleLength - 1;
            //获取前一字符的最短编码长度。
            int preCodeLengthTemp = index == 0 ? 0 : subscriptInstances[index - 1].getCodeLengthTemp();
            //判断每个长度是否有词
            int cursor = index;
            strTemp = subscriptInstances[index].getWord();
            TrieWordLib.TrieNode node = wordLib.getNode(strTemp);
            //词库中有以该字开头的词语，并且剩余文章字数大于等于2
            while (node != null && node.getChildren() != null && ++cursor <= articleMaxIndex) {
                String currentWord = subscriptInstances[cursor].getWord();
                strTemp += currentWord;
                node = node.getChildren().get(currentWord);
                if (node != null && node.getCode() != null) {
                    codeTemp = deleteDefaultUpSymbol(node.getCode(), subscriptInstances, cursor);
                    int nextCodeLengthTemp = preCodeLengthTemp + codeTemp.length();
                    // TODO type需要重写
                    subscriptInstances[cursor].addPre(nextCodeLengthTemp, index, strTemp, codeTemp, typeConvert.convert(codeTemp));
                    if (subscriptInstances[cursor].getCodeLengthTemp() == 0 ||
                            subscriptInstances[cursor].getCodeLengthTemp() > nextCodeLengthTemp) {
                        subscriptInstances[cursor].setCodeLengthTemp(nextCodeLengthTemp);
                    }
                }
            }
            /*
              （判断该下标的最短编码长度有无设置

              无->分支1：若无设置，即前面遍历都没有遇到词，下标j处为单字，将该下标设置为上一跳最短编码长度+该下标单字编码长度）

              有->分支2：[说明该处必为某词的最后一字]（该下标最短编码长度）是否大于（上一跳最短编码长度+该字符编码长度）
                    是->说明上一跳的词不为最短编码，将上一跳删除，并将该处最短编码设置为后者。
             */
            int wordCodeLength = subscriptInstances[index].getWordCode().length();
            int thisCodeLength = subscriptInstances[index].getCodeLengthTemp();
            int nextCodeLengthTemp = preCodeLengthTemp + wordCodeLength;
            if (thisCodeLength == 0) {
                subscriptInstances[index].setCodeLengthTemp(nextCodeLengthTemp);
            } else if (thisCodeLength > nextCodeLengthTemp) {
                subscriptInstances[index].setCodeLengthTemp(nextCodeLengthTemp);
            }
        }
    }

    /**
     * 逆向进行dp计算最短编码。
     */
    public void reverseDp(SubscriptInstance[] subscriptInstances) {
        /*
          结束了所有增加上一跳操作后，从后往前跳（因为最后一格为最短编码，一直往上一跳绝对为最短路径）

          优先->执行循环从前往后遍历，每次循环优先找i的最佳编码的上一跳bestPre，跳一次后将bestPre点的下一跳设置为i
                将bestPre设置为已使用词组始位useWordSign设置为true,再将bestPre到i全部将词组覆盖标记useSign设置为true

          次优先->执行完优先下跳后，遍历i点的所有上一跳pre，判断是否满足（pre>bestPre且没有词组覆盖过pre）
                是->所有的pre的下一跳都设置为i，并将已使用词组始位useWordSign设置为true

          直接将遍历提前到bestPre
         */
        for (int i = subscriptInstances.length - 1; i >= 0; i--) {
            SubscriptInstance subscriptInstance = subscriptInstances[i];
            PreInfo preInfo = subscriptInstance.getMinPre();
            int pre = 0;
            if (preInfo != null) {
                pre = preInfo.getPre();
            }
            //最短编码词提
            if (preInfo != null && !subscriptInstances[pre].isUseWordSign()
                    && !(!subscriptInstances[pre].isUseSign() && subscriptInstances[i].isUseSign())
            ) {
                subscriptInstances[pre].setNext(i);
                subscriptInstances[pre].setType(preInfo.getType());
                subscriptInstances[pre].setWords(preInfo.getWords());
                subscriptInstances[pre].setWordsCode(preInfo.getWordsCode());
                subscriptInstances[pre].setUseWordSign(true);
                for (int i2 = pre; i2 <= i; i2++) {
                    subscriptInstances[i2].setUseSign(true);
                }
            }
            //错过最短编码后的动态词提
            for (Integer key : subscriptInstance.getPreInfoMap().keySet()) {
                TreeMap<Integer, PreInfo> preInfoTreeMap = subscriptInstances[i].getPreInfoMap().get(key);
                for (Map.Entry<Integer, PreInfo> entry : preInfoTreeMap.entrySet()) {
                    int preTemp = entry.getKey();
                    PreInfo preInfoTemp = entry.getValue();
                    if (preTemp > pre && !subscriptInstances[preTemp].isUseWordSign()) {
                        subscriptInstances[preTemp].setNext(i);
                        subscriptInstances[preTemp].setType(preInfoTemp.getType());
                        subscriptInstances[preTemp].setWords(preInfoTemp.getWords());
                        subscriptInstances[preTemp].setWordsCode(preInfoTemp.getWordsCode());
                    }
                }
            }
            if (preInfo != null) {
                i = pre;
            }
        }
    }

    public String printCode(SubscriptInstance[] subscriptInstances) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < subscriptInstances.length; i++) {
            SubscriptInstance subscriptInstance = subscriptInstances[i];
            if (subscriptInstance.getNext() == i) {
                stringBuilder.append(subscriptInstance.getWordCode());
            } else {
                stringBuilder.append(subscriptInstance.getWordsCode());
            }
            i = subscriptInstance.getNext();
        }
        return stringBuilder.toString();
    }
}
