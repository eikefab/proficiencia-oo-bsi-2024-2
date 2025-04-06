package br.edu.ifal.eikefab.account.menu;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountController;
import br.edu.ifal.eikefab.menu.options.DeleteAccountOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteAccountOptionTest {

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
    public void shouldDeleteExistingAccountSuccessfully() {
        UUID id = UUID.randomUUID();

        Account account = Account.builder()
                .uniqueId(id)
                .name("Carlos")
                .email("carlos@example.com")
                .balance(1000)
                .checking();

        AccountController.getInstance().create(account);

        String input = id + "\n";
        DeleteAccountOption option = new DeleteAccountOption();
        option.getHandler().onSelect(simulateInput(input));

        String output = getConsoleOutput();
        assertTrue(output.contains("Conta excluída."));
        assertFalse(AccountController.getInstance().exists(id));
    }

    @Test
    public void shouldShowMessageWhenAccountDoesNotExist() {
        UUID fakeId = UUID.randomUUID();

        String input = fakeId + "\n";
        DeleteAccountOption option = new DeleteAccountOption();
        option.getHandler().onSelect(simulateInput(input));

        String output = getConsoleOutput();
        assertTrue(output.contains("A conta informada não existe."));
    }
}

