package com.banking.loans.controller;

import com.banking.loans.config.LoansConfig;
import com.banking.loans.dto.response.Properties;
import com.banking.loans.persist.model.Loan;
import com.banking.loans.repository.LoansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("loans")
public class LoansController {

    @Autowired
    private LoansRepository loansRepository;

    @Autowired
    private LoansConfig config;

    @GetMapping("/customer/{customerId}")
    public List<Loan> getCustomerLoans(@PathVariable("customerId") int customerId){
        System.out.println("called Loans");
        return loansRepository.findByCustomerIdOrderByStartDtDesc(customerId);
    }

    @GetMapping("/config")
    public Properties getConfig(){
        Properties properties = new Properties(
                config.getMsg(), config.getBuildVersion()
                , config.getMailDetails(), config.getActiveBranches()
        );
        return properties;
    }

}
