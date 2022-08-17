package com.banking.loans.controller;

import com.banking.loans.config.LoansConfig;
import com.banking.loans.dto.response.Properties;
import com.banking.loans.persist.model.Loan;
import com.banking.loans.repository.LoansRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("loans")
public class LoansController {

    private static final Logger logger = LoggerFactory.getLogger("LoansController");

    @Autowired
    private LoansRepository loansRepository;

    @Autowired
    private LoansConfig config;

    @GetMapping("/customer/{customerId}")
    public List<Loan> getCustomerLoans(@RequestHeader("banking-correlation-id") String correlationId, @PathVariable("customerId") int customerId){
       // System.out.println("called Loans correlationId " + correlationId);
        logger.info("getCustomerLoans start");
        logger.info("getCustomerLoans end");
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
