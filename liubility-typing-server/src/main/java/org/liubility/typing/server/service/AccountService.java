package org.liubility.typing.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.liubility.commons.dto.account.AccountDto;
import org.liubility.typing.server.domain.entity.Account;

/**
 * @Author JDragon
 * @Date 2021.02.11 上午 12:54
 * @Email 1061917196@qq.com
 * @Des:
 */
public interface AccountService extends IService<Account> {
    AccountDto getAccountByName(String username);

    String login(AccountDto accountDto);

    String register(AccountDto accountDto);
}
