package com.katatest.accountmanagement.domain;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Account {
    private Long id;
    private String title;
    private LocalDate creationDate;
    private Double amount;
    private Double balance;
    private List<Client> owners = new ArrayList<>();
    private List<Operation> operationHistory = new ArrayList<>();

    public void addDeferred(Double amount) {
        this.amount += amount;
    }

    public void addInstantaneously(Double amount) {
        this.amount += amount;
        this.balance += amount;
    }

    public void addOperation(Operation operation) {
        this.operationHistory.add(operation);
    }
}
