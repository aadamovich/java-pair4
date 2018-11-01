package com.playtech;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class Bank {

    public static List<Account> accounts;


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
        System.out.println("Enter transaction type (DEPOSIT, WITHDRAWAL):");
        TransactionType transactiontype = TransactionType.valueOf(userInput.next());
        System.out.println("Enter amount:");
        double amount = Double.valueOf(userInput.next());

        transaction(transactiontype, accountNumber, amount);

        accounts.stream().map(a -> a.getAccountnr() + ";" + a.getAccountname() + ";" + a.getBalance())
                .forEach(System.out::println);


    }

    public static void transaction(TransactionType transactionType, long accountnr, double amount) throws IOException {

        Account a = accounts
                .stream()
                .filter(acc -> acc.getAccountnr() == accountnr)
                .findFirst()
                .get();

        switch (transactionType) {

            case DEPOSIT:
                System.out.println("Deposit coming up in amount of " + amount);
                a.setBalance(a.getBalance() + amount);
                break;

            case WITHDRAWAL:
                System.out.println("Withdraw coming up in amount of " + amount);
                a.setBalance(a.getBalance() - amount);
                break;

            default:
                System.out.println("You are stupid error");
                break;

        }

        saveAccounts();

        //add transaction
        addTransaction(transactionType, accountnr, amount);


    }

    private static void addTransaction(TransactionType transactionType, long accountnr, double amount) throws IOException {

        Files.write(Paths.get("transactions.csv"), Collections.singleton(transactionType + ";" +accountnr + ";"+amount), StandardOpenOption.APPEND);
    }

    public static void saveAccounts() throws IOException {

        Files.write(Paths.get("accounts.csv"),
                accounts.stream().map(a -> a.getAccountnr() + ";" + a.getAccountname() + ";" + a.getBalance())
                        .collect(Collectors.toList()));

    }

    public static enum TransactionType {
        DEPOSIT, WITHDRAWAL;
    }
}
