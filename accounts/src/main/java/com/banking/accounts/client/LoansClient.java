package com.banking.accounts.client;

import com.banking.accounts.model.Loan;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("loans")
public interface LoansClient {

    @RequestMapping(method = RequestMethod.GET, value = "/loans/customer/{customerId}", consumes = "application/json")
    List<Loan> getCustomerLoans(@RequestHeader("banking-correlation-id") String correlationId, @PathVariable Integer customerId);
}
