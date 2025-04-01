package br.edu.ifal.eikefab.account.repository;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountType;
import br.edu.ifal.eikefab.account.impl.SavingsAccount;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class AccountRepositoryImplTest {

    private static final AccountRepository REPOSITORY = AccountRepositoryImpl.getInstance();
    private static final UUID RANDOM_UNIQUE_ID = UUID.randomUUID();

    @BeforeAll
    public static void mustHaveConnection() {
        Assertions.assertTrue(REPOSITORY::isConnected);
    }

    @Test
    public void manipulate() {
        final SavingsAccount account = SavingsAccount.builder()
                .uniqueId(RANDOM_UNIQUE_ID)
                .balance(100)
                .email("eike@email.com")
                .name("Eike")
                .savings();

        REPOSITORY.createAccount(account);

        final Optional<Account> optional = REPOSITORY.findAccountByUniqueId(RANDOM_UNIQUE_ID);
        final Set<Account> allAccounts = REPOSITORY.findAllAccounts();
        final Set<Account> allAccountsByName = REPOSITORY.findAllAccountsByName("Eike");

        Assertions.assertTrue(optional.isPresent());

        final Account item = optional.get();

        Assertions.assertEquals(RANDOM_UNIQUE_ID, item.getUniqueId());
        Assertions.assertEquals(100, item.getBalance());
        Assertions.assertEquals("eike@email.com", item.getEmail());
        Assertions.assertEquals(AccountType.SAVINGS, item.getAccountType());

        Assertions.assertFalse(allAccounts.isEmpty());
        Assertions.assertFalse(allAccountsByName.isEmpty());

        account.deposit(40);

        REPOSITORY.updateAccount(account);

        final Optional<Account> query = REPOSITORY.findAccountByUniqueId(RANDOM_UNIQUE_ID);

        Assertions.assertTrue(query.isPresent());
        Assertions.assertEquals(140, query.get().getBalance());

        REPOSITORY.deleteAccount(account.getUniqueId());

        final Optional<Account> delete = REPOSITORY.findAccountByUniqueId(RANDOM_UNIQUE_ID);

        Assertions.assertFalse(delete.isPresent());
    }

    @Test
    public void mustBeEmpty() {
        final Optional<Account> optional = REPOSITORY.findAccountByUniqueId(UUID.randomUUID());

        Assertions.assertTrue(optional.isEmpty());
    }

}
