package org.liubility.typing.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.liubility.commons.controller.BaseController;
import org.liubility.typing.server.domain.entity.Article;
import org.liubility.typing.server.service.TypingHistoryService;
import org.liubility.commons.dto.account.HistoryArticleDto;
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
public class HistoryController extends BaseController {

    @Autowired
    private TypingHistoryService typingHistoryService;

    @PostMapping("/uploadHistoryAndArticle")
    @ApiOperation("上传历史记录(带文章修正解决文章过长不能上传)")
    public Result<String> uploadHistoryAndArticle(@RequestBody HistoryArticleDto historyAndArticle) {
        historyAndArticle.getTypeHistoryDto().setUserId(getUserId());
        return Result.successMsg(typingHistoryService.uploadHistoryAndArticle(historyAndArticle));
    }
}
