package br.edu.ifal.eikefab.account.menu;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountController;
import br.edu.ifal.eikefab.account.exceptions.AccountNegativeTransactionException;
import br.edu.ifal.eikefab.menu.options.DepositAccountOption;
import br.edu.ifal.eikefab.menu.options.exception.AccountNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DepositAccountOptionTest {

    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        AccountController.getInstance().getAccounts().clear();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    private Scanner simulateInput(String data) {
        return new Scanner(new ByteArrayInputStream(data.getBytes()));
    }

    private String getConsoleOutput() {
        return outputStream.toString().trim();
    }

    @Test
    public void shouldDepositSuccessfully() {
        UUID id = UUID.randomUUID();

        Account account = Account.builder()
                .uniqueId(id)
                .name("Alice")
                .email("alice@example.com")
                .balance(100)
                .checking();

        AccountController.getInstance().create(account);

        String simulatedInput = id + "\n250\n"; // deposit 250
        DepositAccountOption option = new DepositAccountOption();
        option.getHandler().onSelect(simulateInput(simulatedInput));

        String output = getConsoleOutput();

        assertTrue(output.contains("Depósito realizado com sucesso."));
        Account updated = AccountController.getInstance().getAccountByUniqueId(id).orElseThrow();
        assertEquals(350, updated.getBalance(), 0.01);
    }

    @Test
    public void shouldThrowExceptionWhenDepositIsNegative() {
        UUID id = UUID.randomUUID();

        Account account = Account.builder()
                .uniqueId(id)
                .name("Bob")
                .email("bob@example.com")
                .balance(500)
                .savings();

        AccountController.getInstance().create(account);

        String simulatedInput = id + "\n-100\n"; // negative value
        DepositAccountOption option = new DepositAccountOption();

        assertThrows(AccountNegativeTransactionException.class, () -> {
            option.getHandler().onSelect(simulateInput(simulatedInput));
        });
    }

    @Test
    public void shouldThrowExceptionWhenAccountDoesNotExist() {
        UUID fakeId = UUID.randomUUID();

        String simulatedInput = fakeId + "\n100\n"; // nonexistent account
        DepositAccountOption option = new DepositAccountOption();

        assertThrows(AccountNotFoundException.class, () -> {
            option.getHandler().onSelect(simulateInput(simulatedInput));
        });
    }
}

