package com.joselucio.accounts.service;

import com.joselucio.accounts.dto.CustomerDto;
import org.springframework.stereotype.Service;

public interface IAccountsService {

    void createAccount(CustomerDto customerDto);


}
