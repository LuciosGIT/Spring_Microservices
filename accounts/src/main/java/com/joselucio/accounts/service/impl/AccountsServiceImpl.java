package com.joselucio.accounts.service.impl;

import com.joselucio.accounts.dto.CustomerDto;
import com.joselucio.accounts.repository.AccountsRepository;
import com.joselucio.accounts.repository.CustomerRepository;
import com.joselucio.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;


    @Override
    public void createAccount(CustomerDto customerDto) {

    }
}
