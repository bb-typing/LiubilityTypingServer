package org.liubility.typing.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.liubility.commons.dto.account.NumDto;
import org.liubility.typing.server.domain.entity.TypingUser;
import org.liubility.typing.server.service.TypingHistoryService;
import org.liubility.typing.server.service.TypingUserService;
import org.liubility.commons.controller.BaseController;
import org.liubility.commons.dto.account.TypeHistoryDto;
import org.liubility.commons.http.response.normal.Result;
import org.liubility.commons.http.response.table.PageTable;
import org.liubility.commons.http.response.table.TableFactory;
import org.liubility.commons.http.response.table.TableRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author JDragon
 * @Date 2021.02.19 下午 8:33
 * @Email 1061917196@qq.com
 * @Des:
 */
@RestController
@RequestMapping("/account/type")
@Api(tags = "个人中心")
public class UserTypeController extends BaseController {
    @Autowired
    private TypingUserService typingUserService;

    @Autowired
    private TypingHistoryService typingHistoryService;

    @GetMapping(value = "/info")
    @ApiOperation("获取个人信息")
    public Result<TypingUser> getMyInfo() {
        return Result.success(typingUserService.getTypeUserById(getUserId()));
    }

    @GetMapping("/history")
    @ApiOperation("获取个人跟打历史")
    public Result<PageTable<TypeHistoryDto>> getMyTypeHistory() {
        IPage<TypeHistoryDto> typeHistoryByUserId = typingHistoryService.getTypeHistoryByUserId(new Page<>(getPageNum(), getPageSize()), getUserId());
        PageTable<TypeHistoryDto> table = TableFactory.buildPageTable(typeHistoryByUserId, new TableRef<TypeHistoryDto>(typeHistoryByUserId.getRecords()) {
        });
        return Result.success(table);
    }

    @PostMapping("/changeNum")
    @ApiOperation("更改字数")
    public Result<NumDto> changeNum(@RequestBody NumDto numDto) {
        return Result.success(typingUserService.changeNum(getUserId(), numDto));
    }
}
