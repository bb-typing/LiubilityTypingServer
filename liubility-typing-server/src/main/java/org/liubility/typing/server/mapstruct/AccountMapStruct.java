package org.liubility.typing.server.mapstruct;

import org.liubility.commons.dto.account.AccountDto;
import org.liubility.typing.server.domain.entity.Account;
import org.mapstruct.Mapper;

/**
 * @Author JDragon
 * @Date 2021.02.11 上午 1:23
 * @Email 1061917196@qq.com
 * @Des:
 */

@Mapper(componentModel = "spring")
public interface AccountMapStruct {
    AccountDto ToDto(Account account);

    Account dtoToAccount(AccountDto account);
}
