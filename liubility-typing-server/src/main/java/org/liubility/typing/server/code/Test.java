package org.liubility.typing.server.code;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.liubility.typing.server.code.compare.CompareFeelDeviationWeights;
import org.liubility.typing.server.code.convert.MockTypeConvert;
import org.liubility.typing.server.code.libs.EnglishTrieWordLib;
import org.liubility.typing.server.code.libs.TrieWordLib;
import org.liubility.typing.server.code.parse.SubscriptInstance;
import org.liubility.typing.server.code.parse.TrieWordParser;
import org.liubility.typing.server.code.reader.FileReaderFactory;
import org.liubility.typing.server.code.reader.MinioReaderFactory;
import org.liubility.typing.server.code.reader.ReaderFactory;
import org.liubility.typing.server.compare.ArticleComparator;
import org.liubility.typing.server.compare.ComparisonItem;
import org.liubility.typing.server.minio.Minio;
import org.liubility.typing.server.minio.MinioProperties;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @Author: JDragon
 * @Data:2022/9/2 1:14
 * @Description:
 */
public class Test {

    public static final ArticleComparator articleComparator = new ArticleComparator();

    public static final TrieWordLib symbol, wordLib;

    public static final TrieWordParser trieWordParser;

    static {
        MinioProperties minioProperties = new MinioProperties();
        minioProperties.setHost("192.168.1.150");
        minioProperties.setPort(9000);
        minioProperties.setAccessKey("minioadmin");
        minioProperties.setSecretKey("minioadmin");
        Minio minio = Minio.builder()
                .endpoint("http://" + minioProperties.getHost() + ":" + minioProperties.getPort())
                .accessKey(minioProperties.getAccessKey())
                .secretKey(minioProperties.getSecretKey())
                .build();
        minio.init();

        ReaderFactory readerFactory = new MinioReaderFactory(minio);

        symbol = new TrieWordLib(readerFactory, "symbol.txt", "", 0, "");
//        wordLib = new TrieWordLib(readerFactory, "jb.txt", "23456789", 4, ";'");
//        wordLib.merge(symbol);
        wordLib = new EnglishTrieWordLib(readerFactory, "jb.txt");

        CompareFeelDeviationWeights compareFeelDeviationWeights = new CompareFeelDeviationWeights(0.5, 0.5, wordLib.getFilterDuplicateSymbols());
        compareFeelDeviationWeights.addKeyBoardPartition("1qaz");
        compareFeelDeviationWeights.addKeyBoardPartition("2wsx");
        compareFeelDeviationWeights.addKeyBoardPartition("3edc");
        compareFeelDeviationWeights.addKeyBoardPartition("45rfvtgb");
        compareFeelDeviationWeights.addKeyBoardPartition("67yhnujm");
        compareFeelDeviationWeights.addKeyBoardPartition("8ik,");
        compareFeelDeviationWeights.addKeyBoardPartition("9ol.");
        compareFeelDeviationWeights.addKeyBoardPartition("0p;/'");
        compareFeelDeviationWeights.addKeyBoardPartition("_");

        trieWordParser = new TrieWordParser(wordLib, symbol, new MockTypeConvert("23456789", wordLib.getDefaultUpSymbol()), compareFeelDeviationWeights);
    }

    public static void main(String[] args) {
        testLib();
    }

    public static void testCompare() {
        List<ComparisonItem> comparisonItemList = articleComparator.comparison("main函数内，调用算法类的入口吗", "main函数内调用算法的入的口吗", true, symbol);
        System.out.println(comparisonItemList);
    }

    public static void testLib() {
        String str = "shit";
        SubscriptInstance[] parse = trieWordParser.parse(str);
        String s = trieWordParser.printCode(parse);
        System.out.println(s);
    }
}
