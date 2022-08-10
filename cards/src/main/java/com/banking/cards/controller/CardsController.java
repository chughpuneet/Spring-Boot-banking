package com.banking.cards.controller;

import com.banking.cards.config.CardsConfig;
import com.banking.cards.dto.response.Properties;
import com.banking.cards.persist.model.Card;
import com.banking.cards.repository.CardsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cards")
public class CardsController {

    private static final Logger logger = LoggerFactory.getLogger(CardsController.class);

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private CardsConfig config;

    @GetMapping("/customer/{customerId}")
    public List<Card> getCardDetailsForCustomer(@PathVariable("customerId") Integer customerId) {
        logger.info("getCardDetails() method started");
        List<Card> cards = cardsRepository.findByCustomerId(customerId);
        logger.info("getCardDetails() method ended");

        return cards;
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
