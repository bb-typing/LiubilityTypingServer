package org.liubility.typing.server.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.liubility.commons.dto.account.AccountDto;
import org.liubility.commons.exception.AuthException;
import org.liubility.commons.exception.LBRuntimeException;
import org.liubility.commons.jwt.JwtServiceImpl;
import org.liubility.typing.server.enums.exception.Code201Account;
import org.liubility.typing.server.mappers.AccountMapper;
import org.liubility.typing.server.domain.entity.Account;
import org.liubility.typing.server.mapstruct.AccountMapStruct;
import org.liubility.typing.server.service.AccountService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author JDragon
 * @Date 2021.02.11 上午 12:54
 * @Email 1061917196@qq.com
 * @Des:
 */
@Slf4j
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    private final JwtServiceImpl jwtService;

    private final AccountMapStruct accountMapStruct;

    private final StringRedisTemplate stringRedisTemplate;

    public AccountServiceImpl(JwtServiceImpl jwtService, AccountMapStruct accountMapStruct, StringRedisTemplate stringRedisTemplate) {
        this.jwtService = jwtService;
        this.accountMapStruct = accountMapStruct;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public AccountDto getAccountByName(String username) {
        LambdaQueryWrapper<Account> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Account::getUsername, username);
        Account account = this.getOne(lambdaQueryWrapper);
        return accountMapStruct.ToDto(account);
    }

    @Override
    public String login(AccountDto accountDto) {
        log.info("登录用户{}，ip:{}", accountDto.getUsername(), accountDto.getIp());
        String password = SecureUtil.md5(accountDto.getPassword());
        AccountDto loginAccountByName = getAccountByName(accountDto.getUsername());
        if (loginAccountByName == null) {
            throw new AuthException("用户不存在");
        }
        if (!loginAccountByName.getPassword().equals(password)) {
            throw new AuthException("密码错误");
        }
        loginAccountByName.setIp(accountDto.getIp());
        String token = jwtService.generateToken(loginAccountByName);
        stringRedisTemplate.opsForSet().add("lb:allow-ips:" + loginAccountByName.getId(), accountDto.getIp());
        stringRedisTemplate.opsForValue().set("lb:token:" + loginAccountByName.getId(), token);
        return token;
    }

    @Override
    public String register(AccountDto accountDto) {
        String username = accountDto.getUsername();
        String password = SecureUtil.md5(accountDto.getPassword());
        AccountDto existAccount = getAccountByName(username);
        if (existAccount != null) {
            throw new LBRuntimeException(Code201Account.USER_EXIST);
        }
        Account account = accountMapStruct.dtoToAccount(accountDto);
        account.setPassword(password);
        if (account.insert()) {
            return "注册成功";
        } else {
            throw new UnknownError("注册失败");
        }
    }
}
