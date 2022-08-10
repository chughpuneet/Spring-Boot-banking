package com.banking.cards.repository;

import com.banking.cards.persist.model.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardsRepository extends CrudRepository<Card, Integer> {
    List<Card> findByCustomerId(int customerId);
}
