package com.tenpo.lromero.transacsback.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tenpo.lromero.transacsback.dto.TransactionRequest;
import com.tenpo.lromero.transacsback.dto.TransactionResponse;
import com.tenpo.lromero.transacsback.interfaces.TransactionService;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    void testCreateTransaction() {
        TransactionRequest request = new TransactionRequest();
        TransactionResponse response = new TransactionResponse();

        when(transactionService.create(any(TransactionRequest.class))).thenReturn(response);

        TransactionResponse result = transactionController.createTransaction(request);

        assertThat(result).isEqualTo(response);
        verify(transactionService).create(any(TransactionRequest.class));
    }

    @Test
    void testGetAllTransactions() {
        List<TransactionResponse> responses = Arrays.asList(new TransactionResponse(), new TransactionResponse());
        when(transactionService.findAll()).thenReturn(responses);

        List<TransactionResponse> result = transactionController.getAllTransactions();

        assertThat(result).isEqualTo(responses);
        verify(transactionService).findAll();
    }

    @Test
    void testGetTransactionById() {
        Integer id = 1;
        TransactionResponse response = new TransactionResponse();
        when(transactionService.findById(id)).thenReturn(response);

        TransactionResponse result = transactionController.getTransactionById(id);

        assertThat(result).isEqualTo(response);
        verify(transactionService).findById(id);
    }

    @Test
    void testUpdateTransaction() {
        Integer id = 1;
        TransactionRequest request = new TransactionRequest();
        TransactionResponse response = new TransactionResponse();
        when(transactionService.update(id, request)).thenReturn(response);

        TransactionResponse result = transactionController.updateTransaction(id, request);

        assertThat(result).isEqualTo(response);
        verify(transactionService).update(id, request);
    }

    @Test
    void testDeleteTransaction() {
        Integer id = 1;

        transactionController.deleteTransaction(id);

        verify(transactionService).delete(id);
    }
}
