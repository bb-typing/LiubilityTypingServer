package org.liubility.typing.server.code;

import org.liubility.typing.server.code.libs.TrieWordLib;
import org.liubility.typing.server.code.parse.SubscriptInstance;
import org.liubility.typing.server.code.parse.TrieWordParser;

/**
 * @Author: JDragon
 * @Data:2022/9/2 1:14
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        TrieWordLib wordLib = new TrieWordLib("wordlib.txt", "23456789", 4, ";'");
        wordLib.init();
        TrieWordLib symbol = new TrieWordLib("symbol.txt", "", 0, "");
        symbol.init();

        wordLib.merge(symbol);

        TrieWordParser trieWordParser = new TrieWordParser(wordLib, symbol);
        SubscriptInstance[] parse = trieWordParser.parse("我是不知道是谁搞事哦！你不知道，我也不知道。");
        String s = trieWordParser.printCode(parse);
        System.out.println(s);
    }
}
