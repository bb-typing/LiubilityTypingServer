package org.liubility.typing.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.liubility.commons.controller.BaseController;
import org.liubility.commons.dto.account.AccountDto;
import org.liubility.commons.http.response.normal.Result;
import org.liubility.typing.server.service.AccountService;
import org.liubility.commons.jwt.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author JDragon
 * @Date 2021.02.11 上午 12:53
 * @Email 1061917196@qq.com
 * @Des:
 */

@RestController
@RequestMapping("/account")
@Api(tags = "用户账号")
public class AccountController extends BaseController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtServiceImpl jwtService;

    @PostMapping("/login")
    @ApiOperation("登录")
    public Result<String> login(@RequestBody AccountDto accountDto) {
        return Result.success(accountService.login(accountDto));
    }

    @PostMapping(value = "/register")
    @ApiOperation(value = "注册")
    public Result<String> register(@RequestBody AccountDto accountDto) {
        return Result.success(accountService.register(accountDto));
    }

    @PostMapping("/refreshToken")
    @ApiOperation("刷新令牌")
    public Result<String> refreshToken() {
        return Result.success(jwtService.refreshToken(getToken()));
    }
}
