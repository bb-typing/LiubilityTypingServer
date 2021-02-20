package org.liubility.typing.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.liubility.typing.server.domain.entity.TypeUser;
import org.liubility.typing.server.service.TypeHistoryService;
import org.liubility.typing.server.service.TypeUserService;
import org.liubility.commons.controller.BaseController;
import org.liubility.commons.dto.account.TypeHistoryDto;
import org.liubility.commons.exception.LBException;
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
@RequestMapping("/account/me")
@Api(tags = "个人中心")
public class UserTypeController extends BaseController {
    @Autowired
    private TypeUserService typeUserService;

    @Autowired
    private TypeHistoryService typeHistoryService;

    @PostMapping(value = "/info")
    @ApiOperation("获取个人信息")
    public Result<TypeUser> getMyInfo(@RequestHeader Integer userId) throws LBException {
        return Result.success(typeUserService.getTypeUserById(userId));
    }

    @GetMapping("/history")
    @ApiOperation("获取个人跟打历史")
    public Result<PageTable<TypeHistoryDto>> getMyTypeHistory(@RequestHeader Integer userId,
                                                              @RequestParam Integer pageNum,
                                                              @RequestParam Integer pageSize) {
        IPage<TypeHistoryDto> typeHistoryByUserId = typeHistoryService.getTypeHistoryByUserId(new Page<>(pageNum, pageSize), userId);
        PageTable<TypeHistoryDto> table = TableFactory.buildPageTable(typeHistoryByUserId, new TableRef<TypeHistoryDto>(typeHistoryByUserId.getRecords()) {
        });
        return Result.success(table);
    }
}
