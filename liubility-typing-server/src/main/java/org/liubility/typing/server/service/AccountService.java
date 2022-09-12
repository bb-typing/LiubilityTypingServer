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
    /**
     * 根据名称获取账号
     *
     * @param username 用户名
     * @return AccountDto
     */
    AccountDto getAccountByName(String username);

    /**
     * 登录接口
     *
     * @param accountDto 账号密码
     * @return token
     */
    String login(AccountDto accountDto);

    /**
     * 注册
     *
     * @param accountDto 账号密码
     * @return message
     */
    String register(AccountDto accountDto);
}
