package org.liubility.account.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.liubility.account.domain.entity.Article;
import org.liubility.account.domain.entity.TypeHistory;
import org.liubility.account.mapstruct.ArticleMapStruct;
import org.liubility.account.mapstruct.TypeHistoryMapStruct;
import org.liubility.account.service.TypeHistoryService;
import org.liubility.commons.dto.account.HistoryArticleDto;
import org.liubility.commons.exception.LBException;
import org.liubility.commons.http.response.normal.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author JDragon
 * @Date 2021.02.19 下午 8:31
 * @Email 1061917196@qq.com
 * @Des:
 */

@RestController
@RequestMapping("/account/history")
@Api(tags = "历史记录")
public class HistoryController {

    @Autowired
    private TypeHistoryService typeHistoryService;

    @PostMapping("/uploadHistoryAndArticle")
    @ApiOperation("上传历史记录(带文章修正解决文章过长不能上传)")
    public Result<String> uploadHistoryAndArticle(@RequestHeader Integer userId,
                                                  @RequestBody HistoryArticleDto historyAndArticle) throws LBException {
        historyAndArticle.getTypeHistoryDto().setUserId(userId);
        return Result.success(typeHistoryService.uploadHistoryAndArticle(historyAndArticle));
    }
}
