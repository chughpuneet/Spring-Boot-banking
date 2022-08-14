package com.banking.accounts.controller;

import com.banking.accounts.client.CardsClient;
import com.banking.accounts.client.LoansClient;
import com.banking.accounts.config.AccountsConfig;
import com.banking.accounts.dto.response.Properties;
import com.banking.accounts.model.Card;
import com.banking.accounts.model.CustomerDetails;
import com.banking.accounts.model.Loan;
import com.banking.accounts.persist.entity.Account;
import com.banking.accounts.repository.AccountsRepository;
import com.sun.istack.NotNull;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("accounts")
public class AccountsController {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private AccountsConfig config;

    @Autowired
    private LoansClient loansClient;

    @Autowired
    private CardsClient cardsClient;

    @GetMapping("")
    public Account getAccountDetails(@RequestParam @NotNull Integer customerId) {

        Account accounts = accountsRepository.findByCustomerId(customerId);
        if (accounts != null) {
            return accounts;
        } else {
            return null;
        }

    }

    @GetMapping("/customer/{customerId}")
    @CircuitBreaker(name = "getCustomerAccountDetailsCircuitBreaker", fallbackMethod = "getCustomerAccountDetailsFallBack")
    public CustomerDetails getCustomerAccountDetails(@PathVariable("customerId") Integer customerId) {

        Account account = accountsRepository.findByCustomerId(customerId);
        List<Card> cards = cardsClient.getCardDetailsForCustomer(customerId);
        List<Loan> loans = loansClient.getCustomerLoans(customerId);

        return new CustomerDetails(account,cards, loans);

    }

    private CustomerDetails getCustomerAccountDetailsFallBack(
            Integer customerId, Throwable t){
        Account account = accountsRepository.findByCustomerId(customerId);
       // List<Card> cards = cardsClient.getCardDetailsForCustomer(customerId);
        List<Loan> loans = loansClient.getCustomerLoans(customerId);
        return new CustomerDetails(account,null, loans);
    }

    @GetMapping("/config")
    public Properties getConfig(){
        com.banking.accounts.dto.response.Properties properties = new Properties(
                config.getMsg(), config.getBuildVersion()
                , config.getMailDetails(), config.getActiveBranches()
        );
        return properties;
    }



}
