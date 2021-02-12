package org.liuibility.security.controller;

import org.liubility.commons.dto.account.AccountDto;
import org.liubility.commons.http.response.normal.Result;
import org.liuibility.security.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author JDragon
 * @Date 2021.02.11 上午 12:53
 * @Email 1061917196@qq.com
 * @Des:
 */

@RestController
@RequestMapping("/user")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/getLoginAccountByName")
    public Result<AccountDto> getLoginAccountByName(@RequestParam String username){
        return null;
    }
}
