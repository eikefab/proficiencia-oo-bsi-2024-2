package br.edu.ifal.eikefab.account.menu;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountController;
import br.edu.ifal.eikefab.menu.options.ListAccountOption;
import br.edu.ifal.eikefab.menu.options.exception.NoAccountsException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ListAccountOptionTest {

    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        AccountController.getInstance().getAccounts().clear();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    private String getConsoleOutput() {
        return outputStream.toString().trim();
    }

    @Test
    public void shouldListAllAccountsWhenAccountsExist() {
        Account account1 = Account.builder()
                .uniqueId(UUID.randomUUID())
                .name("João")
                .email("joao@email.com")
                .balance(500)
                .savings();

        Account account2 = Account.builder()
                .uniqueId(UUID.randomUUID())
                .name("Maria")
                .email("maria@email.com")
                .balance(1500)
                .checking();

        AccountController.getInstance().create(account1);
        AccountController.getInstance().create(account2);

        ListAccountOption option = new ListAccountOption();
        option.getHandler().onSelect(null);

        String output = getConsoleOutput();

        assertTrue(output.contains("Existem"));
        assertTrue(output.contains("contas cadastradas no sistema"));
        assertTrue(output.contains(account1.getName()));
        assertTrue(output.contains(account2.getName()));
    }

    @Test
    public void shouldNotThrowWhenTheresAnAccount() {
        ListAccountOption option = new ListAccountOption();

        assertDoesNotThrow(() -> {
            option.getHandler().onSelect(null);
        });
    }
}

