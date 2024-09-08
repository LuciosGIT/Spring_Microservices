package com.joselucio.accounts.mapper;

import com.joselucio.accounts.dto.AccountsDto;
import com.joselucio.accounts.entity.Accounts;

public class AccountsMapper {

    public static AccountsDto mapToAccountsDto(Accounts accounts, AccountsDto accountsDto) {

        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAdress(accounts.getBranchAddress());
        return accountsDto;
    }

    public static Accounts mapToAccounts(AccountsDto accountsDto, Accounts accounts) {
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAdress());
        return accounts;
    }

}
