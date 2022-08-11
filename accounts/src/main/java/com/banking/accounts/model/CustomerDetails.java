package com.banking.accounts.model;

import com.banking.accounts.persist.entity.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @RequiredArgsConstructor
public class CustomerDetails {
    private final Account account;
    private final List<Card> cards;
    private final List<Loan> loans;
}
