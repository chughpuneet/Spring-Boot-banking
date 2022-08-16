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
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    //@CircuitBreaker(name = "getCustomerAccountDetailsCircuitBreaker", fallbackMethod = "getCustomerAccountDetailsFallBack")
    @Retry(name = "getCustomerAccountDetailsRetry", fallbackMethod = "getCustomerAccountDetailsFallBack")
    public CustomerDetails getCustomerAccountDetails(@RequestHeader("banking-correlation-id") String correlationId, @PathVariable("customerId") Integer customerId) {

        System.out.println("correlationId " + correlationId);
        Account account = accountsRepository.findByCustomerId(customerId);
        List<Loan> loans = loansClient.getCustomerLoans(correlationId, customerId);
        List<Card> cards = cardsClient.getCardDetailsForCustomer(correlationId, customerId);

        return new CustomerDetails(account,cards, loans);

    }

    private CustomerDetails getCustomerAccountDetailsFallBack(
            String correlationId, Integer customerId, Throwable t){
        Account account = accountsRepository.findByCustomerId(customerId);
       // List<Card> cards = cardsClient.getCardDetailsForCustomer(customerId);
        List<Loan> loans = loansClient.getCustomerLoans(correlationId, customerId);
        return new CustomerDetails(account,null, loans);
    }

    @GetMapping("/config")
    @RateLimiter(name = "getConfigRateLimiter", fallbackMethod = "getConfigRateLimiterFallBack")
    public Properties getConfig(){
        com.banking.accounts.dto.response.Properties properties = new Properties(
                config.getMsg(), config.getBuildVersion()
                , config.getMailDetails(), config.getActiveBranches()
        );
        return properties;
    }

    /*
    The fallback method for rate limiter gets called only when the thread doesn't go to run state after passing the timeout period
     */
    private Properties getConfigRateLimiterFallBack(Throwable t){
        return new Properties(null, null, null, null);
    }

}
