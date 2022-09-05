package org.liubility.typing.server.code;

import org.liubility.typing.server.code.compare.CompareCodeLengthWeights;
import org.liubility.typing.server.code.compare.CompareFeelDeviationWeights;
import org.liubility.typing.server.code.convert.MockTypeConvert;
import org.liubility.typing.server.code.libs.TrieWordLib;
import org.liubility.typing.server.code.parse.SubscriptInstance;
import org.liubility.typing.server.code.parse.TrieWordParser;
import org.liubility.typing.server.compare.ArticleComparator;
import org.liubility.typing.server.compare.ComparisonItem;

import java.util.List;

/**
 * @Author: JDragon
 * @Data:2022/9/2 1:14
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        testLib();
    }

    public static void testCompare() {
        TrieWordLib symbol = new TrieWordLib("symbol.txt", "", 0, "");
        ArticleComparator articleComparator = new ArticleComparator();
        List<ComparisonItem> comparisonItemList = articleComparator.comparison("main函数内，调用算法类的入口吗", "main函数内调用算法的入的口吗", true, symbol);
        System.out.println(comparisonItemList);
    }

    public static void testLib() {
        TrieWordLib wordLib = new TrieWordLib("wordlib.txt", "23456789", 4, ";'");
        TrieWordLib symbol = new TrieWordLib("symbol.txt", "", 0, "");
        wordLib.merge(symbol);
        CompareFeelDeviationWeights compareFeelDeviationWeights = new CompareFeelDeviationWeights();
        compareFeelDeviationWeights.addKeyBoardPartition("1qaz");
        compareFeelDeviationWeights.addKeyBoardPartition("2wsx");
        compareFeelDeviationWeights.addKeyBoardPartition("3edc");
        compareFeelDeviationWeights.addKeyBoardPartition("45rfvtgb");
        compareFeelDeviationWeights.addKeyBoardPartition("67yhnujm");
        compareFeelDeviationWeights.addKeyBoardPartition("8ik,");
        compareFeelDeviationWeights.addKeyBoardPartition("9ol.");
        compareFeelDeviationWeights.addKeyBoardPartition("0p;/'");
        compareFeelDeviationWeights.addKeyBoardPartition("_");
        TrieWordParser trieWordParser = new TrieWordParser(wordLib, symbol, new MockTypeConvert("23456789", wordLib.getDefaultUpSymbol()), compareFeelDeviationWeights);
        SubscriptInstance[] parse = trieWordParser.parse("我很快地明白这一切。毕竟，这次体验让我更加认识自我。我的中心认同是自我而非工作，虽然后者也颇重要。哦，几乎忘了告诉你我的新工作，薪资比原来的还多。尽管经历了一段不算短的黑暗期，但最后的结果令我十分满意。");
        String s = trieWordParser.printCode(parse);
        System.out.println(s);
    }
}
