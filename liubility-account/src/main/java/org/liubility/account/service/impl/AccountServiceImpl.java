package org.liubility.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.liubility.commons.dto.account.AccountDto;
import org.liubility.commons.exception.AuthException;
import org.liubility.commons.jwt.JwtServiceImpl;
import org.liubility.account.mappers.AccountMapper;
import org.liubility.account.domain.entity.Account;
import org.liubility.account.mapstruct.AccountMapStruct;
import org.liubility.account.service.AccountService;
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
    private JwtServiceImpl jwtService;

    @Autowired
    private AccountMapStruct accountMapStruct;

    @Override
    public AccountDto getAccountByName(String username) {
        LambdaQueryWrapper<Account> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Account::getUsername,username);
        Account account = this.getOne(lambdaQueryWrapper);
        return accountMapStruct.accountToDto(account);
    }

    @Override
    public String login(AccountDto accountDto) throws AuthException {
        AccountDto loginAccountByName = getAccountByName(accountDto.getUsername());
        if(loginAccountByName == null){
            throw new AuthException("用户不存在");
        }
        if(!loginAccountByName.getPassword().equals(accountDto.getPassword())){
            throw new AuthException("密码错误");
        }
        return jwtService.generateToken(accountDto);
    }
}
