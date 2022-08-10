package com.banking.accounts.repository;

import com.banking.accounts.persist.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends CrudRepository<Account, Long> {

    public Account findByCustomerId(int customerId);
}
