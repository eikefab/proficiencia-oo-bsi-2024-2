package br.edu.ifal.eikefab.account.menu;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountController;
import br.edu.ifal.eikefab.account.AccountType;
import br.edu.ifal.eikefab.menu.options.CreateAccountOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CreateAccountOptionTest {

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
    public void shouldCreateSavingsAccount() {
        String simulatedInput = "\nMaria Silva\nmaria@email.com\n1\n"; // 1 = poupança
        CreateAccountOption option = new CreateAccountOption();
        option.getHandler().onSelect(input(simulatedInput));

        Set<Account> accounts =  AccountController.getInstance().getAccountsByName("Maria Silva");
        assertFalse(accounts.isEmpty());
    }

    @Test
    public void shouldCreateCheckingsAccount() {
        String simulatedInput = "\nJoão Lima\njoao@email.com\n2\n"; // 2 = corrente
        CreateAccountOption option = new CreateAccountOption();
        option.getHandler().onSelect(input(simulatedInput));

        Set<Account> accounts =  AccountController.getInstance().getAccountsByName("João Lima");
        assertFalse(accounts.isEmpty());
    }

    @Test
    public void shouldRejectIfInvalidThenAcceptIfValid() {
        String simulatedInput = "\nAna Costa\nana@email.com\n3\n0\n2\n"; // opções inválidas, depois válida
        CreateAccountOption option = new CreateAccountOption();
        option.getHandler().onSelect(input(simulatedInput));

        String output = getOutput();
        assertTrue(output.contains("Opção inválida!"));
        assertTrue(output.contains("Conta criada com sucesso."));

        Set<Account> accounts =  AccountController.getInstance().getAccountsByName("Ana Costa");
        assertFalse(accounts.isEmpty());
    }
}

