package org.liuibility.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.liubility.commons.dto.account.AccountDto;
import org.liuibility.security.domain.entity.Account;
import org.liuibility.security.mapstruct.AccountMapStruct;
import org.liuibility.security.mappers.AccountMapper;
import org.liuibility.security.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author JDragon
 * @Date 2021.02.11 上午 12:54
 * @Email 1061917196@qq.com
 * @Des:
 */

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper,Account> implements AccountService {

    @Autowired
    private AccountMapStruct accountMapStruct;

    @Override
    public AccountDto getLoginAccountByName(String username) {
        LambdaQueryWrapper<Account> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Account::getUsername,username);
        Account account = this.getOne(lambdaQueryWrapper);
        return null;
    }
}
