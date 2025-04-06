package br.edu.ifal.eikefab.account.menu;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountController;
import br.edu.ifal.eikefab.account.exceptions.AccountBalanceException;
import br.edu.ifal.eikefab.account.exceptions.AccountNegativeTransactionException;
import br.edu.ifal.eikefab.menu.options.WithdrawAccountOption;
import br.edu.ifal.eikefab.menu.options.exception.AccountNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class WithdrawAccountOptionTest {

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
    public void shouldWithdrawSuccessfully() {
        UUID id = UUID.randomUUID();

        Account account = Account.builder()
                .uniqueId(id)
                .name("Lucas")
                .email("lucas@example.com")
                .balance(500)
                .checking();

        AccountController.getInstance().create(account);

        String simulatedInput = id + "\n150\n";
        WithdrawAccountOption option = new WithdrawAccountOption();
        option.getHandler().onSelect(simulateInput(simulatedInput));

        String output = getConsoleOutput();

        assertTrue(output.contains("Saque realizado com sucesso."));
        Account updated = AccountController.getInstance().getAccountByUniqueId(id).orElseThrow();
        assertEquals(350, updated.getBalance(), 0.01);
    }

    @Test
    public void shouldThrowExceptionForNegativeWithdraw() {
        UUID id = UUID.randomUUID();

        Account account = Account.builder()
                .uniqueId(id)
                .name("Ana")
                .email("ana@example.com")
                .balance(400)
                .savings();

        AccountController.getInstance().create(account);

        String simulatedInput = id + "\n-50\n";
        WithdrawAccountOption option = new WithdrawAccountOption();

        assertThrows(AccountNegativeTransactionException.class, () -> {
            option.getHandler().onSelect(simulateInput(simulatedInput));
        });
    }

    @Test
    public void shouldThrowExceptionForWithdrawExceedingBalance() {
        UUID id = UUID.randomUUID();

        Account account = Account.builder()
                .uniqueId(id)
                .name("Bruna")
                .email("bruna@example.com")
                .balance(300)
                .checking();

        AccountController.getInstance().create(account);

        String simulatedInput = id + "\n500\n"; // more than balance
        WithdrawAccountOption option = new WithdrawAccountOption();

        assertThrows(AccountBalanceException.class, () -> {
            option.getHandler().onSelect(simulateInput(simulatedInput));
        });
    }

    @Test
    public void shouldThrowExceptionWhenAccountDoesNotExist() {
        UUID fakeId = UUID.randomUUID();

        String simulatedInput = fakeId + "\n100\n";
        WithdrawAccountOption option = new WithdrawAccountOption();

        assertThrows(AccountNotFoundException.class, () -> {
            option.getHandler().onSelect(simulateInput(simulatedInput));
        });
    }
}

