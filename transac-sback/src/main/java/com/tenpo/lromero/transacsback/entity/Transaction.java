package com.tenpo.lromero.transacsback.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer amount;

    private String commerce;

    private LocalDateTime transactionDate;

    @ManyToOne
    @JoinColumn(name = "tenpista_id", nullable = false)
    private Tenpista tenpista;

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

    public Tenpista getTenpista() {
        return tenpista;
    }

    public void setTenpista(Tenpista tenpista) {
        this.tenpista = tenpista;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", commerce='" + commerce + '\'' +
                ", tenpistaName='" + tenpista.getName() + '\'' +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
