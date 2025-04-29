package com.tenpo.lromero.transacsback.interfaces;

import java.util.List;

import com.tenpo.lromero.transacsback.dto.TransactionRequest;
import com.tenpo.lromero.transacsback.dto.TransactionResponse;

public interface TransactionService {

    TransactionResponse create(TransactionRequest transaction);

    TransactionResponse update(Integer id, TransactionRequest transaction);

    void delete(Integer id);

    List<TransactionResponse> findAll();

    TransactionResponse findById(Integer id);
}
