package com.playtech;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.playtech.TransactionType.DEPOSIT;
import static com.playtech.TransactionType.WITHDRAWAL;
import static java.nio.file.Files.lines;
import static java.nio.file.Files.write;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toList;

public class Bank {

    private List<Account> accounts;

    private TransactionType transactionType;

    public Bank() {
        accounts = new ArrayList<>();
        transactionType = WITHDRAWAL;
    }


    public double getTotalBankAmount() {
        return accounts.stream().mapToDouble(Account::getBalance).sum();
    }

    private static Account convertAccount(String[] fields) {
        return new Account(
                fields[1],
                Long.valueOf(fields[0]),
                Double.valueOf(fields[2])
        );
    }

    public void transaction(long accountNr, TransactionType transactionType, long targetAccount, double amount) throws IOException {

        switch (transactionType) {

            case TRANSFER:
                updateBalance(WITHDRAWAL, accountNr, amount);
                updateBalance(DEPOSIT, targetAccount, amount);
                break;

            default:
                updateBalance(transactionType, accountNr, amount);

        }

    }

    private void updateBalance(TransactionType transactionType, long accountnr, double amount) throws IOException {

        Optional<Account> a = accounts
                .stream()
                .filter(acc -> acc.getAccountnr() == accountnr)
                .findFirst();

        if (!a.isPresent()) {
            throw new RuntimeException("Account not found!");
        }

        System.out.println(transactionType + " coming up in amount of " + amount);

        Account account = a.get();
        switch (transactionType) {

            case DEPOSIT:
                account.setBalance(account.getBalance() + amount);
                break;

            case WITHDRAWAL:
                account.setBalance(account.getBalance() - amount);
                break;
        }

        saveAccounts();
        System.out.println("Account summary:  " + a);
        addTransaction(transactionType, accountnr, amount);

    }




    private void addTransaction(TransactionType transactionType, long accountnr, double amount) throws IOException {
        write(get("transactions.csv"),
                singleton(transactionType + ";" + accountnr + ";" + amount),
                APPEND);
    }

    public void loadAccounts() throws IOException {
        accounts = lines(get("accounts.csv"))
                .map(line -> line.split(";"))
                .map(Bank::convertAccount)
                .collect(toList());
    }

    private void saveAccounts() throws IOException {
        write(get("accounts.csv"),
                accounts
                        .stream()
                        .map(Bank::convertAccountToCsvRecord)
                        .collect(toList()));
    }

    private static String convertAccountToCsvRecord(Account a) {
        return a.getAccountnr() + ";" + a.getAccountname() + ";" + a.getBalance();
    }

}
