package com.tenpo.lromero.transacsback.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response payload representing a transaction record")
public class TransactionResponse {

    @Schema(description = "Unique identifier of the transaction", example = "1")
    private Integer id;
    @Schema(description = "Transaction amount in pesos", example = "10000")
    private Integer amount;
    @Schema(description = "Name of the commerce or business where the transaction occurred", example = "Tienda El Cacha")
    private String commerce;
    @Schema(description = "Name of the Tenpista (user) associated with the transaction", example = "Marlon Cabarcas")
    private String tenpistaName;
    @Schema(description = "Date and time when the transaction occurred", example = "2025-04-28T10:15:30")
    private LocalDateTime transactionDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
