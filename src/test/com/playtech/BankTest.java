package test.com.playtech;

import com.playtech.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.playtech.TransactionType.DEPOSIT;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BankTest {

    private Bank bank;

    @BeforeEach
    void setup() throws IOException {

        bank = new Bank();
        bank.loadAccounts();
    }

    @Test
    void testAddition() throws IOException {

        double currentAmount = bank.getTotalBankAmount();
        bank.transaction(111111, DEPOSIT, 0, 10);

        assertEquals(currentAmount + 10, bank.getTotalBankAmount());

    }

}