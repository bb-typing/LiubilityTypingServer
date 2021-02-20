package org.liubility.account.mapstruct;

import javax.annotation.Generated;
import org.liubility.account.domain.entity.Account;
import org.liubility.commons.dto.account.AccountDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-02-20T00:25:40+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_241 (Oracle Corporation)"
)
@Component
public class AccountMapStructImpl implements AccountMapStruct {

    @Override
    public AccountDto ToDto(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountDto accountDto = new AccountDto();

        accountDto.setUsername( account.getUsername() );
        accountDto.setPassword( account.getPassword() );

        return accountDto;
    }

    @Override
    public Account dtoToAccount(AccountDto account) {
        if ( account == null ) {
            return null;
        }

        Account account1 = new Account();

        account1.setUsername( account.getUsername() );
        account1.setPassword( account.getPassword() );

        return account1;
    }
}
