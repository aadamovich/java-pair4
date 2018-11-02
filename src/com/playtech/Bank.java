package com.playtech;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

import static com.playtech.Bank.TransactionType.*;

public class Bank {

    private static List<Account> accounts;

    public static void main(String[] args) throws IOException {

//        List<Account> accounts = Arrays.asList(
//            new Account ("Meelis Tolk", 111111, 10),
//            new Account ("Siim Kallson", 222222, 100),
//            new Account ("Donald Trump", 999999, 10000000)
//        );

        // transactiontype 1 = depo
        // transactiontype 2 = withdraw

        accounts = Files.lines(Paths.get("accounts.csv"))
                .map((String line) -> line.split(";"))
                .map((String[] fields) -> new Account(fields[1], Long.valueOf(fields[0]), Double.valueOf(fields[2])))
                .collect(Collectors.toList());


        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter account number:");
        long accountNumber = Long.valueOf(userInput.next());
        System.out.println("Enter transaction type (DEPOSIT, WITHDRAWAL, TRANSFER):");
        TransactionType transactiontype = valueOf(userInput.next());
        long targetAccountNumber = 0;
        if(transactiontype.equals(TRANSFER)){
            System.out.println("Enter transfer target account number:");
            targetAccountNumber = Long.valueOf(userInput.next());
        }

        System.out.println("Enter amount:");
        double amount = Double.valueOf(userInput.next());

        transaction(transactiontype, accountNumber, amount, targetAccountNumber);

//        accounts.stream().map(a -> a.getAccountnr() + ";" + a.getAccountname() + ";" + a.getBalance())
//                .forEach(System.out::println);


    }

    private static void transaction(TransactionType transactionType, long accountNr, double amount, long targetAccount) throws IOException {

        switch (transactionType) {

            case TRANSFER:
                updateBalance(WITHDRAWAL, accountNr, amount);
                updateBalance(DEPOSIT, targetAccount, amount);
                break;

            default:
                updateBalance(transactionType, accountNr, amount);
                break;
        }
    }

    private static void updateBalance(TransactionType transactionType, long accountnr, double amount) throws IOException {
        Account a = accounts
                .stream()
                .filter(acc -> acc.getAccountnr() == accountnr)
                .findFirst()
                .get();
        System.out.println(transactionType+" coming up in amount of " + amount);

        switch (transactionType) {

            case DEPOSIT:
                a.setBalance(a.getBalance() + amount);
                break;

            case WITHDRAWAL:
                a.setBalance(a.getBalance() - amount);
                break;
        }

        saveAccounts();
        System.out.println("Account summary:  " + a);
        addTransaction(transactionType, accountnr, amount);
    }

    private static void addTransaction(TransactionType transactionType, long accountnr, double amount) throws IOException {
        Files.write(Paths.get("transactions.csv"), Collections.singleton(transactionType + ";" +accountnr + ";"+amount), StandardOpenOption.APPEND);
    }

    private static void saveAccounts() throws IOException {

        Files.write(Paths.get("accounts.csv"),
                accounts.stream().map(a -> a.getAccountnr() + ";" + a.getAccountname() + ";" + a.getBalance())
                        .collect(Collectors.toList()));

    }

    public enum TransactionType {
        DEPOSIT, WITHDRAWAL,TRANSFER
    }
}
