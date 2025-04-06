package br.edu.ifal.eikefab.account.menu;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountController;
import br.edu.ifal.eikefab.menu.options.ListAccountByNameOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ListAccountByNameOptionTest {

    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        AccountController.getInstance().getAccounts().clear();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    private Scanner simulateInput(String input) {
        return new Scanner(new ByteArrayInputStream(input.getBytes()));
    }

    private String getConsoleOutput() {
        return outputStream.toString().trim();
    }

    @Test
    public void shouldListAccountsByNameSuccessfully() {
        Account account1 = Account.builder()
                .uniqueId(UUID.randomUUID())
                .name("Lucas Silva")
                .email("lucas@email.com")
                .balance(700)
                .checking();

        Account account2 = Account.builder()
                .uniqueId(UUID.randomUUID())
                .name("Lucas Moura")
                .email("moura@email.com")
                .balance(1200)
                .savings();

        Account account3 = Account.builder()
                .uniqueId(UUID.randomUUID())
                .name("Maria")
                .email("maria@email.com")
                .balance(800)
                .savings();

        AccountController.getInstance().create(account1);
        AccountController.getInstance().create(account2);
        AccountController.getInstance().create(account3);

        String simulatedInput = "\nLucas\n";  // Note the first \n is to skip scanner.nextLine()
        ListAccountByNameOption option = new ListAccountByNameOption();
        option.getHandler().onSelect(simulateInput(simulatedInput));

        String output = getConsoleOutput();

        assertTrue(output.contains("resultados encontrados para Lucas"));
        assertTrue(output.contains(account1.getName()));
        assertTrue(output.contains(account2.getName()));
        assertFalse(output.contains(account3.getName()));
    }

    @Test
    public void shouldThrowExceptionWhenNoMatchingAccountsFound() {
        Account account = Account.builder()
                .uniqueId(UUID.randomUUID())
                .name("Fernanda")
                .email("fernanda@email.com")
                .balance(1000)
                .checking();

        AccountController.getInstance().create(account);

        String simulatedInput = "\nFernanda\n"; // Carlos not in the list
        ListAccountByNameOption option = new ListAccountByNameOption();

        assertDoesNotThrow(() -> {
            option.getHandler().onSelect(simulateInput(simulatedInput));
        });
    }
}

