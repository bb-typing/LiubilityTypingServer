package org.liubility.typing.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.liubility.commons.http.response.normal.Result;
import org.liubility.typing.server.code.parse.SubscriptInstance;
import org.liubility.typing.server.compare.ArticleComparator;
import org.liubility.typing.server.compare.ComparisonItem;
import org.liubility.typing.server.minio.service.MinioServiceImpl;
import org.liubility.typing.server.scheduling.SynEarlierVersionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.liubility.typing.server.code.Test.symbol;
import static org.liubility.typing.server.code.Test.trieWordParser;

/**
 * @Author: JDragon
 * @Data:2022/9/3 1:52
 * @Description:
 */
@RestController
@RequestMapping("/version/test")
@Api(tags = "测试")
public class TestController {


    private final MinioServiceImpl minioService;

    public TestController(MinioServiceImpl minioService, RedisTemplate<String, Object> redisTemplate) {
        this.minioService = minioService;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping(value = "/typingTips")
    @ApiOperation("词提测试")
    public Result<SubscriptInstance[]> typingTips(@RequestBody CodeParam codeParam) {
        SubscriptInstance[] parse = trieWordParser.parse(codeParam.getCode());
        return Result.success(parse);
    }

    @PostMapping(value = "/codeLength")
    @ApiOperation("理论编码")
    public Result<String> codeLength(@RequestBody CodeParam codeParam) {
        SubscriptInstance[] parse = trieWordParser.parse(codeParam.getCode());
        String s = trieWordParser.printCode(parse);
        return Result.success(s);
    }

    @PostMapping(value = "/compare")
    @ApiOperation("看打听打提交成绩后的对比")
    public Result<List<ComparisonItem>> compare(@RequestBody CodeParam codeParam) {
        ArticleComparator articleComparator = new ArticleComparator();
        List<ComparisonItem> comparisonItemList = articleComparator.comparison(codeParam.getOrigin(), codeParam.getTyped(), codeParam.isIgnoreSymbols(), symbol);
        return Result.success(comparisonItemList);
    }

    private final RedisTemplate<String, Object> redisTemplate;

    @PostMapping(value = "/redis")
    @ApiOperation("设置redis值")
    public Result<String> uploadWordLib(@RequestParam String key,
                                        @RequestParam Long value) {
        redisTemplate.opsForValue().set(key, value);
        return Result.success("设置成功");
    }


    @Autowired
    private SynEarlierVersionData synEarlierVersionData;

    @PostMapping(value = "/synData")
    @ApiOperation("数据同步")
    public Result<String> synData() {
        synEarlierVersionData.start();
        return Result.success("启动成功");
    }

    @Data
    public static class CodeParam {

        private String code;

        private String origin;

        private String typed;

        private boolean ignoreSymbols;
    }
}
