package br.edu.ifal.eikefab.account.controller;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountController;
import br.edu.ifal.eikefab.account.AccountType;
import br.edu.ifal.eikefab.account.impl.CheckingAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;

public class AccountControllerTest {

    private static final AccountController CONTROLLER = AccountController.getInstance();

    @BeforeAll
    public static void shouldBeConnected() {
        Assertions.assertTrue(CONTROLLER::isConnected);
    }

    @Test
    public void persistence() {
        final CheckingAccount account = Account
                .builder()
                .uniqueId(UUID.randomUUID())
                .balance(15)
                .email("eike@email.com")
                .name("Eike")
                .checking();

        Assertions.assertDoesNotThrow(() -> CONTROLLER.create(account));

        final Set<Account> accounts = CONTROLLER.getAccounts();
        final Set<Account> accountsByName = CONTROLLER.getAccountsByName("Eike");

        final Function<Collection<Account>, Boolean> matcher = (items) -> items
                .stream()
                .anyMatch((compare) -> compare.getUniqueId().compareTo(account.getUniqueId()) == 0);

        Assertions.assertFalse(accounts.isEmpty());

        Assertions.assertTrue(matcher.apply(accounts));
        Assertions.assertTrue(matcher.apply(accountsByName));

        final Optional<Account> queryAccount = CONTROLLER.getAccountByUniqueId(account.getUniqueId());

        Assertions.assertTrue(queryAccount.isPresent());

        final Account queryItem = queryAccount.get();

        Assertions.assertEquals(account.getName(), queryItem.getName());
        Assertions.assertEquals(account.getEmail(), queryItem.getEmail());
        Assertions.assertEquals(account.getBalance(), queryItem.getBalance());
        Assertions.assertEquals(AccountType.CHECKING, queryItem.getAccountType());
    }

}
