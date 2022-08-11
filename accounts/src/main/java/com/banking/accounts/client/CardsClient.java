package com.banking.accounts.client;

import com.banking.accounts.model.Card;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("cards")
public interface CardsClient {
    @RequestMapping(method = RequestMethod.GET, value = "/cards/customer/{customerId}", consumes = "application/json")
    List<Card> getCardDetailsForCustomer(@PathVariable Integer customerId);
}
