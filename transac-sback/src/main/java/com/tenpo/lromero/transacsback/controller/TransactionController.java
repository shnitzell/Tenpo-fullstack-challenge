package com.tenpo.lromero.transacsback.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenpo.lromero.transacsback.dto.TransactionRequest;
import com.tenpo.lromero.transacsback.dto.TransactionResponse;
import com.tenpo.lromero.transacsback.interfaces.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import static com.tenpo.lromero.transacsback.swagger.SwaggerExamples.*;

@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "Transaction Controller", description = "APIs for managing transactions of Tenpistas")
public class TransactionController {

    private final TransactionService transactionService; // Assuming you have a TransactionService interface

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Create a new transaction", description = "Creates a new transaction for a Tenpista, validating the business rules.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error returned by GlobalExceptionHandler, Invalid request or business rule violation", content = @Content(schema = @Schema(example = ERROR_400_EXAMPLE))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(example = ERROR_500_EXAMPLE)))
    })
    @PostMapping
    public TransactionResponse createTransaction(@Valid @RequestBody TransactionRequest transaction) {
        return transactionService.create(transaction);
    }

    @Operation(summary = "List all transactions", description = "Retrieves all transactions registered.")
    @GetMapping
    public List<TransactionResponse> getAllTransactions() {
        return transactionService.findAll();
    }

    @Operation(summary = "Find transaction by ID", description = "Retrieves a transaction by its ID.")
    @GetMapping("/{id}")
    public TransactionResponse getTransactionById(
            @Parameter(description = "ID of the transaction to retrieve") @PathVariable Integer id) {
        return transactionService.findById(id);
    }

    @Operation(summary = "Update a transaction", description = "Updates an existing transaction with new information.")
    @PutMapping("/{id}")
    public TransactionResponse updateTransaction(
            @Parameter(description = "ID of the transaction to update") @PathVariable Integer id,
            @Valid @RequestBody TransactionRequest transaction) {
        return transactionService.update(id, transaction);
    }

    @Operation(summary = "Delete a transaction", description = "Deletes a transaction by its ID.")
    @DeleteMapping("/{id}")
    public void deleteTransaction(
            @Parameter(description = "ID of the transaction to delete") @PathVariable Integer id) {
        transactionService.delete(id);
    }
}
