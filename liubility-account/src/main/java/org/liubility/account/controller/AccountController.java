package org.liubility.account.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.checkerframework.checker.units.qual.A;
import org.liubility.commons.controller.BaseController;
import org.liubility.commons.dto.account.AccountDto;
import org.liubility.commons.exception.AuthException;
import org.liubility.commons.exception.LBException;
import org.liubility.commons.http.response.normal.Result;
import org.liubility.account.service.AccountService;
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
    public Result<String> login(@RequestBody AccountDto accountDto) throws AuthException {
        return Result.success(accountService.login(accountDto));
    }

    @PostMapping(value = "/register")
    @ApiOperation(value = "注册")
    public Result<String> register(@RequestBody AccountDto accountDto) throws LBException {
        return Result.success(accountService.register(accountDto));
    }

    @PostMapping("/refreshToken")
    @ApiOperation("刷新令牌")
    public Result<String> refreshToken() {
        return Result.success(jwtService.refreshToken(getToken()));
    }
}
