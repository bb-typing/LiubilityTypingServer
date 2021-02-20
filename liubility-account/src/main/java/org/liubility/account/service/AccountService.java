package org.liubility.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.liubility.commons.dto.account.AccountDto;
import org.liubility.commons.exception.AuthException;
import org.liubility.account.domain.entity.Account;
import org.liubility.commons.exception.LBException;

/**
 * @Author JDragon
 * @Date 2021.02.11 上午 12:54
 * @Email 1061917196@qq.com
 * @Des:
 */
public interface AccountService extends IService<Account> {
    AccountDto getAccountByName(String username);

    String login(AccountDto accountDto) throws AuthException;

    String register(AccountDto accountDto) throws LBException;
}
