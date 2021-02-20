package org.liubility.account.controller;

import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.ApiOperation;
import org.liubility.account.domain.entity.Article;
import org.liubility.account.service.ArticleService;
import org.liubility.commons.exception.LBException;
import org.liubility.commons.http.response.normal.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author JDragon
 * @Date 2021.02.20 下午 1:04
 * @Email 1061917196@qq.com
 * @Des:
 */
@RestController
@RequestMapping("/account/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/getHistoryArticle")
    @ApiOperation("通过articleId获取文章详情")
    public Result<Article> getArticleById(@RequestParam Integer articleId) throws LBException {
        return Result.success(articleService.getArticleById(articleId));
    }
}