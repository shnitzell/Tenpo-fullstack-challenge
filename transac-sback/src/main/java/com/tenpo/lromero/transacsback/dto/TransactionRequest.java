package com.tenpo.lromero.transacsback.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Request payload for creating or updating a transaction")
public class TransactionRequest {

    @Schema(description = "Transaction amount in pesos", example = "10000", minimum = "0")
    @NotNull(message = "Amount is required")
    @PositiveOrZero(message = "Amount must be zero or positive")
    private Integer amount;

    @Schema(description = "Name of the commerce or business where the transaction occurred", example = "Tienda El Cacha")
    @NotBlank(message = "Commerce name is required")
    private String commerce;

    @Schema(description = "Name of the Tenpista (user) associated with the transaction", example = "Marlon Cabarcas")
    @NotBlank(message = "Tenpista name is required")
    private String tenpistaName;

    @Schema(description = "Date and time when the transaction occurred", example = "2025-04-28T10:15:30")
    @NotNull(message = "Transaction date is required")
    @PastOrPresent(message = "Transaction date cannot be in the future")
    private LocalDateTime transactionDate;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCommerce() {
        return commerce;
    }

    public void setCommerce(String commerce) {
        this.commerce = commerce;
    }

    public String getTenpistaName() {
        return tenpistaName;
    }

    public void setTenpistaName(String tenpistaName) {
        this.tenpistaName = tenpistaName;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
