package org.liubility.typing.server.code;

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
        TrieWordParser trieWordParser = new TrieWordParser(wordLib, symbol, new MockTypeConvert("23456789", wordLib.getDefaultUpSymbol()));
        SubscriptInstance[] parse = trieWordParser.parse("应该可以了，该完善的都完善了。");
        String s = trieWordParser.printCode(parse);
        System.out.println(s);
    }
}
