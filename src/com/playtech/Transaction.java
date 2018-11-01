package com.playtech;

public class Transaction {

    private String transactionType;
    private long amount;
    private long accountNumber;

    public Transaction(String transactionType, long amount, long accountNumber) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.accountNumber = accountNumber;
    }


}
