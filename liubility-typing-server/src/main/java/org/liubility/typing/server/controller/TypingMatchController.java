package org.liubility.typing.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.liubility.commons.controller.BaseController;
import org.liubility.commons.exception.LBException;
import org.liubility.commons.http.response.normal.Result;
import org.liubility.typing.server.domain.entity.TypingHistory;
import org.liubility.typing.server.domain.vo.TypingMatchVO;
import org.liubility.typing.server.service.TypingMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author JDragon
 * @Date 2021.08.29 下午 10:09
 * @Email 1061917196@qq.com
 * @Des:
 */

@RestController
@RequestMapping("/account/match")
@Api(tags = "生稿赛")
public class TypingMatchController extends BaseController {

    @Autowired
    private TypingMatchService typingMatchService;

    @GetMapping("/today")
    @ApiOperation("获取今日生稿赛文")
    public Result<TypingMatchVO> getTodayMatch(@ApiParam(name = "mobile", value = "是否为手机") @RequestParam boolean mobile) throws LBException {
        return Result.success(typingMatchService.getTodayMatch(getUserId(), mobile));
    }

    @PostMapping("/uploadTljMatchAch")
    @ApiOperation("上传生稿赛成绩")
    public Result<String> uploadTljMatchAch(@ApiParam(name = "typingHistory", value = "历史成绩Json") @RequestBody TypingHistory typingHistory) {
        return Result.successMsg(typingMatchService.uploadMatch(getUserId(), typingHistory));
    }
}
