package com.tenpo.lromero.transacsback.repository;

import com.tenpo.lromero.transacsback.entity.Tenpista;
import com.tenpo.lromero.transacsback.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    long countByTenpista(Tenpista tenpista);
}