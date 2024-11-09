package com.joselucio.accounts.service.impl;

import com.joselucio.accounts.dto.AccountsDto;
import com.joselucio.accounts.dto.CardsDto;
import com.joselucio.accounts.dto.CustomerDetailsDto;
import com.joselucio.accounts.dto.LoansDto;
import com.joselucio.accounts.entity.Accounts;
import com.joselucio.accounts.entity.Customer;
import com.joselucio.accounts.exception.ResourceNotFoundException;
import com.joselucio.accounts.mapper.AccountsMapper;
import com.joselucio.accounts.mapper.CustomerMapper;
import com.joselucio.accounts.repository.AccountsRepository;
import com.joselucio.accounts.repository.CustomerRepository;
import com.joselucio.accounts.service.ICustomerService;
import com.joselucio.accounts.service.client.CardsFeignClient;
import com.joselucio.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private AccountsRepository accountsRepository;

    private CustomerRepository customerRepository;

    private CardsFeignClient cardsFeignClient;

    private LoansFeignClient loansFeignClient;


    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId"
                        , customer.getCustomerId().toString()));

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        if(null != loansDtoResponseEntity) {
            customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
        }

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
        if(null != cardsDtoResponseEntity) {
            customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        }
        return customerDetailsDto;


    }

}
