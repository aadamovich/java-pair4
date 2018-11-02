package com.playtech;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

import static com.playtech.Bank.TransactionType.*;
import static java.nio.file.Files.lines;
import static java.nio.file.Files.write;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toList;

public class Bank {

  private static List<Account> accounts;

  private static TransactionType transactiontype = WITHDRAWAL;

  public static void main(String[] args) throws IOException {

    accounts = lines(get("accounts.csv"))
        .map(line -> line.split(";"))
        .map(Bank::convertAccount)
        .collect(toList());

    transaction(
        getAccountNumber(),
        getTransactionType(),
        getTargetAccountNumber(),
        getAmount()
    );

  }

  private static Scanner userInput() {
    return new Scanner(System.in);
  }

  private static double getAmount() {
    System.out.println("Enter amount:");
    return Double.valueOf(userInput().next());
  }

  private static long getTargetAccountNumber() {
    long targetAccountNumber = 0;
    if (transactiontype.equals(TRANSFER)) {
      System.out.println("Enter transfer target account number:");
      targetAccountNumber = Long.valueOf(userInput().next());
    }
    return targetAccountNumber;
  }

  private static TransactionType getTransactionType() {
    System.out.println("Enter transaction type (DEPOSIT, WITHDRAWAL, TRANSFER):");
    transactiontype = valueOf(userInput().next());
    return transactiontype;
  }

  private static long getAccountNumber() {
    System.out.println("Enter account number:");
    return Long.valueOf(userInput().next());
  }

  private static Account convertAccount(String[] fields) {
    return new Account(
        fields[1],
        Long.valueOf(fields[0]),
        Double.valueOf(fields[2])
    );
  }

  private static void transaction(long accountNr, TransactionType transactionType, long targetAccount, double amount) throws IOException {

    switch (transactionType) {

      case TRANSFER:
        updateBalance(WITHDRAWAL, accountNr, amount);
        updateBalance(DEPOSIT, targetAccount, amount);
        break;

      default:
        updateBalance(transactionType, accountNr, amount);

    }

  }

  private static void updateBalance(TransactionType transactionType, long accountnr, double amount) throws IOException {

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

  private static void addTransaction(TransactionType transactionType, long accountnr, double amount) throws IOException {
    write(get("transactions.csv"),
        singleton(transactionType + ";" + accountnr + ";" + amount),
        APPEND);
  }

  private static void saveAccounts() throws IOException {
    write(get("accounts.csv"),
        accounts
            .stream()
            .map(Bank::convertAccountToCsvRecord)
            .collect(toList()));
  }

  private static String convertAccountToCsvRecord(Account a) {
    return a.getAccountnr() + ";" + a.getAccountname() + ";" + a.getBalance();
  }

  public enum TransactionType {
    DEPOSIT, WITHDRAWAL, TRANSFER
  }

}
