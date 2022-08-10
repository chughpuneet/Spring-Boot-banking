package com.banking.loans.repository;

import com.banking.loans.persist.model.Loan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoansRepository  extends CrudRepository<Loan, Long> {
    List<Loan> findByCustomerIdOrderByStartDtDesc(int customerId);
}
