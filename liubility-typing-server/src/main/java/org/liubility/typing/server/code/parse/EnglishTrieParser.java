package org.liubility.typing.server.code.parse;

import lombok.extern.slf4j.Slf4j;
import org.liubility.typing.server.code.compare.CompareWeights;
import org.liubility.typing.server.code.convert.WordTypeConvert;
import org.liubility.typing.server.code.libs.TrieWordLib;

/**
 * @Author: JDragon
 * @Data:2022/9/20 22:52
 * @Description:
 */
@Slf4j
public class EnglishTrieParser extends TrieWordParser {

    public EnglishTrieParser(TrieWordLib wordLib, TrieWordLib symbolLib, WordTypeConvert typeConvert, CompareWeights compareWeights) {
        super(wordLib, symbolLib, typeConvert, compareWeights);
    }

    @Override
    protected SubscriptInstance[] createSubscriptInstance(String article) {
        SubscriptInstance[] subscriptInstances = new SubscriptInstance[article.length()];
        int articleLength = article.length();
        String codeTemp, strTemp;
        for (int index = articleLength - 1; index >= 0; index--) {
            strTemp = article.substring(index, index + 1);
            codeTemp = getWordLib().getCode(strTemp);
            if (codeTemp == null && (codeTemp = getSymbolLib().getCode(strTemp)) == null) {
                codeTemp = strTemp + "?";
            }
            SubscriptInstance subscriptInstance = new SubscriptInstance(index, strTemp, codeTemp);

            subscriptInstances[index] = subscriptInstance;
        }
        int index = 0;
        while ((index = article.indexOf(' ', index)) != -1) {

        }
        return subscriptInstances;
    }
}
