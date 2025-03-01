package br.edu.ifal.eikefab.account;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface AccountRepository {

    void connect();
    void disconnect();

    Set<Account> findAllAccounts();
    Set<Account> findAllAccountsByName(String name);
    Optional<Account> findAccountByUniqueId(UUID uuid);

    void createAccount(Account account);
    void updateAccount(Account account);

    void deleteAccount(UUID uniqueId);

    default void deleteAccount(Account account) {
        deleteAccount(account.getUniqueId());
    }

}
