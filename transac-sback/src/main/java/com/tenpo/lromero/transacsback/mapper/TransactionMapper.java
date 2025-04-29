package com.tenpo.lromero.transacsback.mapper;

import org.springframework.stereotype.Component;

import com.tenpo.lromero.transacsback.dto.TransactionRequest;
import com.tenpo.lromero.transacsback.dto.TransactionResponse;
import com.tenpo.lromero.transacsback.entity.Tenpista;
import com.tenpo.lromero.transacsback.entity.Transaction;

@Component
public class TransactionMapper {
    public Transaction toEntity(TransactionRequest request, Tenpista tenpista) {
        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setCommerce(request.getCommerce());
        transaction.setTransactionDate(request.getTransactionDate());
        transaction.setTenpista(tenpista);
        return transaction;
    }

    public TransactionResponse toResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setAmount(transaction.getAmount());
        response.setCommerce(transaction.getCommerce());
        response.setTenpistaName(transaction.getTenpista().getName());
        response.setTransactionDate(transaction.getTransactionDate());
        return response;
    }
}
