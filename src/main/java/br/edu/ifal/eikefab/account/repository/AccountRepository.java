package br.edu.ifal.eikefab.account.repository;

import br.edu.ifal.eikefab.account.Account;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

interface AccountRepository {

    void connect() throws ClassNotFoundException, SQLException;
    void disconnect() throws SQLException;
    void init();
    boolean isConnected();

    Set<Account> findAllAccounts();
    Set<Account> findAllAccountsByName(String name);
    Optional<Account> findAccountByUniqueId(UUID uuid);

    void createAccount(Account account);
    void updateAccount(Account account);

    void deleteAccount(UUID uniqueId);

}
