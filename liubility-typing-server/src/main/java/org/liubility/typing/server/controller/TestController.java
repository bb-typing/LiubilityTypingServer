package org.liubility.typing.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.liubility.commons.http.response.normal.Result;
import org.liubility.typing.server.code.libs.TrieWordLib;
import org.liubility.typing.server.code.parse.SubscriptInstance;
import org.liubility.typing.server.code.parse.TrieWordParser;
import org.liubility.typing.server.compare.ArticleComparator;
import org.liubility.typing.server.compare.ComparisonItem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: JDragon
 * @Data:2022/9/3 1:52
 * @Description:
 */
@RestController
@RequestMapping("/version/test")
@Api(tags = "测试")
public class TestController {

    @GetMapping(value = "/typingTips")
    @ApiOperation("词提测试")
    public Result<SubscriptInstance[]> typingTips(@RequestParam String code) {
        TrieWordLib wordLib = new TrieWordLib("wordlib.txt", "23456789", 4, ";'");
        wordLib.init();
        TrieWordLib symbol = new TrieWordLib("symbol.txt", "", 0, "");
        symbol.init();

        wordLib.merge(symbol);

        TrieWordParser trieWordParser = new TrieWordParser(wordLib, symbol);
        SubscriptInstance[] parse = trieWordParser.parse(code);
        return Result.success(parse);
    }

    @GetMapping(value = "/codeLength")
    @ApiOperation("理论编码")
    public Result<String> codeLength(@RequestParam String code) {
        TrieWordLib wordLib = new TrieWordLib("wordlib.txt", "23456789", 4, ";'");
        wordLib.init();
        TrieWordLib symbol = new TrieWordLib("symbol.txt", "", 0, "");
        symbol.init();

        wordLib.merge(symbol);

        TrieWordParser trieWordParser = new TrieWordParser(wordLib, symbol);
        SubscriptInstance[] parse = trieWordParser.parse(code);
        String s = trieWordParser.printCode(parse);
        return Result.success(s);
    }

    @GetMapping(value = "/compare")
    @ApiOperation("看打听打提交成绩后的对比")
    public Result<List<ComparisonItem>> codeLength(@RequestParam String origin,
                                                   @RequestParam String typed,
                                                   @RequestParam boolean ignoreSymbols) {
        TrieWordLib symbol = new TrieWordLib("symbol.txt", "", 0, "");
        symbol.init();
        ArticleComparator articleComparator = new ArticleComparator();
        List<ComparisonItem> comparisonItemList = articleComparator.comparison(origin, typed, ignoreSymbols, symbol);
        return Result.success(comparisonItemList);
    }
}
