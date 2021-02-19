package org.liubility.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.liubility.commons.dto.account.AccountDto;
import org.liubility.commons.exception.AuthException;
import org.liubility.account.domain.entity.Account;

/**
 * @Author JDragon
 * @Date 2021.02.11 上午 12:54
 * @Email 1061917196@qq.com
 * @Des:
 */
public interface AccountService extends IService<Account> {
    public AccountDto getAccountByName(String username);

    public String login(AccountDto accountDto) throws AuthException;
}