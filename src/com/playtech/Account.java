package com.playtech;

public class Account {

    private String accountname;
    private long accountnr;
    private double balance;

    public Account(String accountname, long accountnr, long balance) {
        this.accountname = accountname;
        this.accountnr = accountnr;
        this.balance = balance;
    }

    public String getAccountname() {
        return accountname;
    }

    public long getAccountnr() {
        return accountnr;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountname='" + accountname + '\'' +
                ", accountnr=" + accountnr +
                ", balance=" + balance +
                '}';
    }
}
