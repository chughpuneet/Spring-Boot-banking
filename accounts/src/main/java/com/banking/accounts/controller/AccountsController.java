package com.banking.accounts.controller;

import com.banking.accounts.config.AccountsConfig;
import com.banking.accounts.dto.response.Properties;
import com.banking.accounts.persist.model.Account;
import com.banking.accounts.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("accounts")
public class AccountsController {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private AccountsConfig config;

    @GetMapping("/customer/{customerId}")
    public Account getAccountDetails(@PathVariable("customerId") Integer customerId) {

        Account accounts = accountsRepository.findByCustomerId(customerId);
        if (accounts != null) {
            return accounts;
        } else {
            return null;
        }

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
