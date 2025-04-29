package com.tenpo.lromero.transacsback.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tenpo.lromero.transacsback.dto.TransactionRequest;
import com.tenpo.lromero.transacsback.dto.TransactionResponse;
import com.tenpo.lromero.transacsback.entity.Tenpista;
import com.tenpo.lromero.transacsback.entity.Transaction;
import com.tenpo.lromero.transacsback.mapper.TransactionMapper;
import com.tenpo.lromero.transacsback.repository.TenpistaRepository;
import com.tenpo.lromero.transacsback.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TenpistaRepository tenpistaRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void testCreateTransaction() {
        // Arrange
        TransactionRequest request = new TransactionRequest();
        request.setAmount(100);
        request.setCommerce("LunaPLata");
        request.setTenpistaName("Luis Romero");
        request.setTransactionDate(LocalDateTime.now());

        Tenpista tenpista = new Tenpista();
        tenpista.setId(1);
        tenpista.setName("Luis Romero");

        Transaction transactionEntity = new Transaction();
        transactionEntity.setAmount(100);
        transactionEntity.setCommerce("LunaPLata");
        transactionEntity.setTenpista(tenpista);
        transactionEntity.setTransactionDate(request.getTransactionDate());

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setAmount(100);
        transactionResponse.setCommerce("LunaPLata");
        transactionResponse.setTenpistaName("Luis Romero");
        transactionResponse.setTransactionDate(request.getTransactionDate());

        // Mock
        when(tenpistaRepository.save(any(Tenpista.class))).thenReturn(tenpista);
        when(transactionMapper.toEntity(any(TransactionRequest.class), any(Tenpista.class)))
                .thenReturn(transactionEntity);
        when(transactionMapper.toResponse(any(Transaction.class))).thenReturn(transactionResponse);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transactionEntity);

        // Act
        TransactionResponse result = transactionService.create(request);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getAmount()).isEqualTo(100);
        assertThat(result.getCommerce()).isEqualTo("LunaPLata");
        assertThat(result.getTenpistaName()).isEqualTo("Luis Romero");
    }
}
