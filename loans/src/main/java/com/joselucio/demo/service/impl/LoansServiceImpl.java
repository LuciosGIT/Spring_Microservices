package com.joselucio.demo.service.impl;

import com.joselucio.demo.constants.LoansConstants;
import com.joselucio.demo.dto.LoansDto;
import com.joselucio.demo.entity.Loans;
import com.joselucio.demo.exception.LoanAlreadyExistsException;
import com.joselucio.demo.exception.ResourceNotFoundException;
import com.joselucio.demo.mapper.LoansMapper;
import com.joselucio.demo.repository.LoansRepository;
import com.joselucio.demo.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
       Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
       if (optionalLoans.isPresent()) {
           throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber" + mobileNumber);
       }
       loansRepository.save(createNewLoan(mobileNumber));
    }

    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));

        return LoansMapper.mapToLoansDto(loans, new LoansDto());
    }

    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
        LoansMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);
        return  true;
    }

    public boolean deleteLoan(String mobileNumber) {
        loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
        () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
        loansRepository.deleteByMobileNumber(mobileNumber);
        return  true;
    }



    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }
}
