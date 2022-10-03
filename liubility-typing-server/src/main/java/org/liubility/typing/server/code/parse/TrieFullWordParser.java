package org.liubility.typing.server.code.parse;

import org.liubility.typing.server.code.compare.CompareWeights;
import org.liubility.typing.server.code.convert.WordTypeConvert;
import org.liubility.typing.server.code.libs.TrieFullWordLib;
import org.liubility.typing.server.code.libs.TrieWordLib;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author: JDragon
 * @Data:2022/10/3 13:32
 * @Description:
 */
public class TrieFullWordParser extends TrieWordParser {

    public TrieFullWordParser(TrieWordLib trieFullWordLib, TrieWordLib symbolLib, WordTypeConvert typeConvert, CompareWeights compareWeights) {
        super(trieFullWordLib, symbolLib, typeConvert, compareWeights);
    }

    @Override
    protected void addPre(SubscriptInstance[] subscriptInstances, int preIndex, int cursor, TrieWordLib.TrieNode node, String words) {
        if (node != null && node.getCode() != null) {
            List<String> codesTemp = new LinkedList<>();
            List<String> codes = ((TrieFullWordLib.TrieNode) node).getCodes();
            for (String code : codes) {
                String codeTemp = deleteDefaultUpSymbol(code, subscriptInstances, cursor);
                codesTemp.add(codeTemp);
            }
            String shortCode = codesTemp.get(0);
            double nextWeights = super.compareWeights.compare(subscriptInstances, cursor, preIndex - 1, words, shortCode);
            //添加该下标可打的所有词为上一跳权重
            subscriptInstances[cursor].addPre(nextWeights, preIndex, words, codesTemp, typeConvert.convert(shortCode));
        }
    }
}
