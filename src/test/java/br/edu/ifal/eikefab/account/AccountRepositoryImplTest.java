package br.edu.ifal.eikefab.account;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class AccountRepositoryImplTest {

    private static final AccountRepositoryImpl REPOSITORY = AccountRepositoryImpl.getInstance();
    private static final UUID RANDOM_UNIQUE_ID = UUID.randomUUID();

    @BeforeAll
    public static void mustHaveConnection() {
        Assertions.assertTrue(REPOSITORY::isConnected);
    }

    @Test
    public void insertOne() {
        final SavingsAccount account = SavingsAccount.builder()
                .uniqueId(RANDOM_UNIQUE_ID)
                .balance(100)
                .email("eike@email.com")
                .name("Eike")
                .transactions(new ArrayList<>())
                .savings();

        REPOSITORY.createAccount(account);

        final Optional<Account> optional = REPOSITORY.findAccountByUniqueId(RANDOM_UNIQUE_ID);

        final Set<Account> allAccounts = REPOSITORY.findAllAccounts();
        final Set<Account> allAccountsByName = REPOSITORY.findAllAccountsByName("Eike");

        Assertions.assertTrue(optional.isPresent());

        Assertions.assertFalse(allAccounts.isEmpty());
        Assertions.assertFalse(allAccountsByName.isEmpty());
    }

    @AfterAll
    public static void closeAndClear() {
        REPOSITORY.deleteAccount(RANDOM_UNIQUE_ID);

        final Set<Account> allAccounts = REPOSITORY.findAllAccounts();

        Assertions.assertTrue(allAccounts.isEmpty());
        Assertions.assertDoesNotThrow(REPOSITORY::disconnect);
    }

}
