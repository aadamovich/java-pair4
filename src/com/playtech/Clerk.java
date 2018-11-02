package com.playtech;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static com.playtech.TransactionType.*;
import static java.nio.file.Files.lines;
import static java.nio.file.Files.write;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toList;

public class Clerk {

  private static List<Account> accounts;

  private static TransactionType transactiontype = WITHDRAWAL;

  public static void main(String[] args) throws IOException {

    Bank bank = new Bank();

    bank.loadAccounts();

    bank.transaction(
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


}
