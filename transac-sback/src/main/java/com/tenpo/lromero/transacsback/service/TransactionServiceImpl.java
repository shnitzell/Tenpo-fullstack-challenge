package com.tenpo.lromero.transacsback.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.tenpo.lromero.transacsback.dto.TransactionRequest;
import com.tenpo.lromero.transacsback.dto.TransactionResponse;
import com.tenpo.lromero.transacsback.entity.Tenpista;
import com.tenpo.lromero.transacsback.entity.Transaction;
import com.tenpo.lromero.transacsback.exception.TransactionLimitExceededException;
import com.tenpo.lromero.transacsback.interfaces.TransactionService;
import com.tenpo.lromero.transacsback.mapper.TransactionMapper;
import com.tenpo.lromero.transacsback.repository.TenpistaRepository;
import com.tenpo.lromero.transacsback.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TenpistaRepository tenpistaRepository;

    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TenpistaRepository tenpistaRepository,
            TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.tenpistaRepository = tenpistaRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public TransactionResponse create(TransactionRequest requestTransaction) {

        Tenpista tenpista = tenpistaRepository.findByName(requestTransaction.getTenpistaName())
                .orElseGet(() -> {
                    Tenpista newTenpista = new Tenpista();
                    newTenpista.setName(requestTransaction.getTenpistaName());
                    return tenpistaRepository.save(newTenpista);
                });

        long count = transactionRepository.countByTenpista(tenpista);
        if (count >= 100) {
            throw new TransactionLimitExceededException(
                    "Transaction limit reached for this Tenpista (100 transactions)");
        }

        Transaction transaction = transactionMapper.toEntity(requestTransaction, tenpista);
        Transaction saved = transactionRepository.save(transaction);
        return transactionMapper.toResponse(saved);
    }

    @Override
    public TransactionResponse update(Integer id, TransactionRequest requestTransaction) {

        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + id));

        Tenpista tenpista = tenpistaRepository.findByName(requestTransaction.getTenpistaName())
                .orElseGet(() -> {
                    Tenpista newTenpista = new Tenpista();
                    newTenpista.setName(requestTransaction.getTenpistaName());
                    return tenpistaRepository.save(newTenpista);
                });

        long count = transactionRepository.countByTenpista(tenpista);
        // Si el usuario cambia el tenpista, y el tenpista nuevo ya tiene 100
        // transacciones, bloqueamos.
        if (!existingTransaction.getTenpista().getId().equals(tenpista.getId()) && count >= 100) {
            throw new TransactionLimitExceededException(
                    "Transaction limit reached for this Tenpista (100 transactions)");
        }

        existingTransaction.setAmount(requestTransaction.getAmount());
        existingTransaction.setCommerce(requestTransaction.getCommerce());
        existingTransaction.setTransactionDate(requestTransaction.getTransactionDate());
        existingTransaction.setTenpista(tenpista);

        Transaction updated = transactionRepository.save(existingTransaction);
        return transactionMapper.toResponse(updated);
    }

    @Override
    public void delete(Integer id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public List<TransactionResponse> findAll() {
        return transactionRepository.findAll()
                .stream()
                .map(transactionMapper::toResponse)
                .toList();
    }

    @Override
    public TransactionResponse findById(Integer id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + id));
        return transactionMapper.toResponse(transaction);
    }

}
