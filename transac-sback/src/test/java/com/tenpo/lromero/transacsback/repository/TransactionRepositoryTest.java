package com.tenpo.lromero.transacsback.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tenpo.lromero.transacsback.entity.Tenpista;
import com.tenpo.lromero.transacsback.entity.Transaction;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TenpistaRepository tenpistaRepository;

    @Test
    @DisplayName("Save a Transaction")
    void testSaveAndFindById() {
        Tenpista tenpista = new Tenpista();
        tenpista.setName("Luis Romero");
        tenpista = tenpistaRepository.save(tenpista);

        Transaction transaction = new Transaction();
        transaction.setAmount(100);
        transaction.setCommerce("LunaPLata");
        transaction.setTenpista(tenpista);
        transaction.setTransactionDate(LocalDateTime.now());

        Transaction saved = transactionRepository.save(transaction);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
    }
}
