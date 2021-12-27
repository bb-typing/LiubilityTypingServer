package org.liubility.typing.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.liubility.commons.controller.BaseController;
import org.liubility.commons.exception.LBException;
import org.liubility.commons.http.response.normal.Result;
import org.liubility.commons.http.response.table.PageTable;
import org.liubility.typing.server.domain.entity.TypingHistory;
import org.liubility.typing.server.domain.vo.TypingHistoryVO;
import org.liubility.typing.server.domain.vo.TypingMatchVO;
import org.liubility.typing.server.service.TypingMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @GetMapping("/getPCTljMatchAchByDate")
    @ApiOperation("根据日期获取生稿赛电脑成绩排名")
    public Result<PageTable<TypingHistoryVO>> getPCTljMatchAchByDate(@RequestParam(defaultValue = "#{new java.util.Date()}") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                                                     @RequestParam(defaultValue = "1") Integer matchType,
                                                                     @RequestParam(defaultValue = "0") Boolean mobile) {
        return Result.success(typingMatchService.getMatchAch(date, matchType, mobile));
    }
}
