package br.edu.ifal.eikefab.account.menu;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountController;
import br.edu.ifal.eikefab.menu.options.CheckBalanceOption;
import br.edu.ifal.eikefab.menu.options.exception.AccountNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CheckBalanceOptionTest {

    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setup() {
        AccountController.getInstance().getAccounts().clear();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    private Scanner input(String data) {
        return new Scanner(new ByteArrayInputStream(data.getBytes()));
    }

    private String getOutput() {
        return outputStream.toString().trim();
    }

    @Test
    public void shouldShowBalance() {
        UUID id = UUID.randomUUID();

        Account account = Account.builder()
                .uniqueId(id)
                .name("Beatriz")
                .email("beatriz@email.com")
                .balance(1200.50)
                .checking();

        AccountController.getInstance().create(account);

        String simulatedInput = id + "\n";

        CheckBalanceOption option = new CheckBalanceOption();
        option.getHandler().onSelect(input(simulatedInput));

        String output = getOutput();

        assertTrue(output.contains("Beatriz possui R$ 1.200,5"));
    }

    @Test
    public void shouldThrowWhenNotFound() {
        UUID id = UUID.randomUUID();

        String simulatedInput = id + "\n";

        CheckBalanceOption option = new CheckBalanceOption();

        assertThrows(AccountNotFoundException.class, () -> {
            option.getHandler().onSelect(input(simulatedInput));
        });
    }
}


