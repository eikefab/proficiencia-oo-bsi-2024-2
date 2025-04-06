package br.edu.ifal.eikefab.account.menu;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.exceptions.AccountInvalidMonthException;
import br.edu.ifal.eikefab.account.impl.CheckingAccount;
import br.edu.ifal.eikefab.account.impl.SavingsAccount;
import br.edu.ifal.eikefab.menu.options.exception.UnsupportedAccountTypeException;
import org.junit.jupiter.api.Test;

import br.edu.ifal.eikefab.account.AccountController;
import br.edu.ifal.eikefab.menu.options.CalculateCheckingPriceOption;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CalculateCheckingPriceOptionTest {

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
    public void shouldCalculateMonthPrice() {
        UUID id = UUID.randomUUID();

        CheckingAccount account = Account.builder()
                .uniqueId(id)
                .name("Carlos")
                .email("carlos@email.com")
                .balance(1000)
                .checking();

        AccountController.getInstance().create(account);

        String simulatedInput = id + "\n6\n";
        CalculateCheckingPriceOption option = new CalculateCheckingPriceOption();
        option.getHandler().onSelect(input(simulatedInput));

        String output = getOutput();
        assertTrue(output.contains("Após 6 meses, você terá R$"));
    }

    @Test
    public void shouldShowInvalidMonth() {
        UUID id = UUID.randomUUID();

        CheckingAccount account = Account.builder()
                .uniqueId(id)
                .name("Julia")
                .email("julia@email.com")
                .balance(1000)
                .checking();

        AccountController.getInstance().create(account);

        String simulatedInput = id + "\n0\n";
        CalculateCheckingPriceOption option = new CalculateCheckingPriceOption();

        assertThrows(
                AccountInvalidMonthException.class,
                () -> option.getHandler().onSelect(input(simulatedInput))
        );
    }

    @Test
    public void shouldThrowInvalidAccountType() {
        UUID id = UUID.randomUUID();

        final SavingsAccount savings = Account.builder()
                .uniqueId(id)
                .name("Joana")
                .email("joana@email.com")
                .balance(1000)
                .savings();

        AccountController.getInstance().create(savings);

        String simulatedInput = id + "\n3\n";

        CalculateCheckingPriceOption option = new CalculateCheckingPriceOption();

        assertThrows(UnsupportedAccountTypeException.class, () -> {
            option.getHandler().onSelect(input(simulatedInput));
        });
    }
}
