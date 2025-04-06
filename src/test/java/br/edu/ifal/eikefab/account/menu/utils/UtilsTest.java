package br.edu.ifal.eikefab.account.menu.utils;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountController;
import br.edu.ifal.eikefab.account.AccountType;
import br.edu.ifal.eikefab.menu.options.exception.AccountNotFoundException;
import br.edu.ifal.eikefab.menu.options.exception.UnsupportedAccountTypeException;
import br.edu.ifal.eikefab.menu.options.utils.AccountFinderUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;
import java.util.UUID;

public class UtilsTest {

    private Scanner input(String data) {
        return new Scanner(new ByteArrayInputStream(data.getBytes()));
    }

    @Test
    public void shouldReturnAccountWhenValidUUIDExists() {
        UUID id = UUID.randomUUID();
        Account account = Account.builder()
                .uniqueId(id)
                .name("Alice")
                .email("alice@email.com")
                .balance(1000)
                .checking();

        AccountController.getInstance().create(account);

        Account result = AccountFinderUtils.getAccount(input(id + "\n"));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(id, result.getUniqueId());
    }

    @Test
    public void shouldThrowExceptionWhenUUIDNotFound() {
        UUID id = UUID.randomUUID();

        Scanner scanner = input(id + "\n");

        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            AccountFinderUtils.getAccount(scanner);
        });
    }

    @Test
    public void shouldThrowExceptionWhenUUIDFormatInvalid() {
        Scanner scanner = input("invalid-uuid\n");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            AccountFinderUtils.getAccount(scanner);
        });
    }

    @Test
    public void shouldReturnAccountWhenTypeMatches() {
        UUID id = UUID.randomUUID();
        Account account = Account.builder()
                .uniqueId(id)
                .name("Bob")
                .email("bob@email.com")
                .balance(500)
                .savings();

        AccountController.getInstance().create(account);

        Account result = AccountFinderUtils.getAccount(input(id + "\n"), AccountType.SAVINGS);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(AccountType.SAVINGS, result.getAccountType());
    }

    @Test
    public void shouldThrowUnsupportedAccountTypeException() {
        UUID id = UUID.randomUUID();
        Account account = Account.builder()
                .uniqueId(id)
                .name("Carol")
                .email("carol@email.com")
                .balance(800)
                .checking();

        Assertions.assertThrows(AccountNotFoundException.class,
                () -> AccountFinderUtils.getAccount(input(id + "\n"))
        );

        AccountController.getInstance().create(account);

        Assertions.assertThrows(UnsupportedAccountTypeException.class, () -> {
            AccountFinderUtils.getAccount(input(id + "\n"), AccountType.SAVINGS);
        });

        Assertions.assertEquals(
                0,
                account.getUniqueId()
                        .compareTo(AccountFinderUtils
                                .getAccount(input(id + "\n"), AccountType.CHECKING).getUniqueId()
                        )
        );
    }
}
