package br.edu.ifal.eikefab.account.menu;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountController;
import br.edu.ifal.eikefab.account.exceptions.AccountInvalidMonthException;
import br.edu.ifal.eikefab.account.impl.CheckingAccount;
import br.edu.ifal.eikefab.account.impl.SavingsAccount;
import br.edu.ifal.eikefab.menu.options.CalculateSavingsOption;
import br.edu.ifal.eikefab.menu.options.exception.UnsupportedAccountTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CalculateSavingsOptionTest {

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
    public void shouldCalculateSavingsRate() {
        UUID id = UUID.randomUUID();

        SavingsAccount savings = SavingsAccount.builder()
                .uniqueId(id)
                .name("Lucas")
                .email("lucas@email.com")
                .balance(1000.0)
                .savings();

        AccountController.getInstance().create(savings);

        String simulatedInput = id + "\n6\n"; // 6 meses
        CalculateSavingsOption option = new CalculateSavingsOption();
        option.getHandler().onSelect(input(simulatedInput));

        String output = getOutput();
        assertTrue(output.contains("Após 6 meses, você terá R$"));
    }

    @Test
    public void deveLancarExcecaoQuandoMesForInvalido() {
        UUID id = UUID.randomUUID();

        SavingsAccount savings = SavingsAccount.builder()
                .uniqueId(id)
                .name("Marina")
                .email("marina@email.com")
                .balance(500)
                .savings();

        AccountController.getInstance().create(savings);

        String simulatedInput = id + "\n0\n";
        CalculateSavingsOption option = new CalculateSavingsOption();

        assertThrows(AccountInvalidMonthException.class, () -> {
            option.getHandler().onSelect(input(simulatedInput));
        });
    }

    @Test
    public void deveLancarExcecaoQuandoTipoContaIncorreto() {
        UUID id = UUID.randomUUID();

        CheckingAccount checking = Account.builder()
                .uniqueId(id)
                .name("Felipe")
                .email("felipe@email.com")
                .balance(700)
                .checking();

        AccountController.getInstance().create(checking);

        String simulatedInput = id.toString() + "\n3\n";
        CalculateSavingsOption option = new CalculateSavingsOption();

        assertThrows(UnsupportedAccountTypeException.class, () -> {
            option.getHandler().onSelect(input(simulatedInput));
        });
    }
}

