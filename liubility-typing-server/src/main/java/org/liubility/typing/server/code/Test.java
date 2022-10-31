package org.liubility.typing.server.code;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import io.minio.errors.*;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.C;
import org.liubility.typing.server.code.compare.CompareCodeLengthWeights;
import org.liubility.typing.server.code.compare.CompareFeelDeviationWeights;
import org.liubility.typing.server.code.convert.MockTypeConvert;
import org.liubility.typing.server.code.libs.EnglishTrieWordLib;
import org.liubility.typing.server.code.libs.TrieFullWordLib;
import org.liubility.typing.server.code.libs.TrieWordLib;
import org.liubility.typing.server.code.parse.SubscriptInstance;
import org.liubility.typing.server.code.parse.TrieFullWordParser;
import org.liubility.typing.server.code.parse.TrieWordParser;
import org.liubility.typing.server.code.reader.FileReaderFactory;
import org.liubility.typing.server.code.reader.MinioReaderFactory;
import org.liubility.typing.server.code.reader.ReaderFactory;
import org.liubility.typing.server.compare.ArticleComparator;
import org.liubility.typing.server.compare.ComparisonItem;
import org.liubility.typing.server.minio.Minio;
import org.liubility.typing.server.minio.MinioProperties;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

    public static final Minio minio;

    static {
        MinioProperties minioProperties = new MinioProperties();
        minioProperties.setHost("192.168.1.150");
        minioProperties.setPort(9000);
        minioProperties.setAccessKey("minioadmin");
        minioProperties.setSecretKey("minioadmin");
        minio = Minio.builder()
                .endpoint("http://" + minioProperties.getHost() + ":" + minioProperties.getPort())
                .accessKey(minioProperties.getAccessKey())
                .secretKey(minioProperties.getSecretKey())
                .build();
        minio.init();

        long start = System.currentTimeMillis();
        ReaderFactory readerFactory = new MinioReaderFactory(minio);

        symbol = new TrieWordLib(readerFactory, "symbol.txt", "", 0, "");
//        wordLib = new TrieWordLib(readerFactory, "jb.txt", "23456789", 4, ";'");
//        wordLib.merge(symbol);
//        wordLib = new EnglishTrieWordLib(readerFactory, "jb.txt");
        wordLib = new TrieFullWordLib(readerFactory, "wordlib.txt", "23456789", 4, ";'");
        wordLib.merge(symbol);

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

        CompareCodeLengthWeights compareCodeLengthWeights = new CompareCodeLengthWeights();

        trieWordParser = new TrieFullWordParser(wordLib, symbol, new MockTypeConvert("23456789", wordLib.getDefaultUpSymbol()), compareFeelDeviationWeights);
        long end = System.currentTimeMillis();
        System.out.println("词库初始化耗时:" + DateUtil.formatBetween(end - start));
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        String text = getTestText("苏菲的世界.txt");
        long end = System.currentTimeMillis();
        System.out.println("加载文章耗时:" + DateUtil.formatBetween(end - start));
        testLib(text);
        System.exit(0);
    }

    public static String getTestText(String file) throws Exception {
        InputStream inputStream = minio.getObject("test", file);
        return IoUtil.readUtf8(inputStream);
    }

    public static void testCompare() {
        List<ComparisonItem> comparisonItemList = articleComparator.comparison("main函数内，调用算法类的入口吗", "main函数内调用算法的入的口吗", true, symbol);
        System.out.println(comparisonItemList);
    }

    public static void testLib(String text) {
        long start = System.currentTimeMillis();
        SubscriptInstance[] parse = trieWordParser.parse(text);
        long end = System.currentTimeMillis();
        System.out.println("解析词提耗时:" + DateUtil.formatBetween(end - start));
    }
}
