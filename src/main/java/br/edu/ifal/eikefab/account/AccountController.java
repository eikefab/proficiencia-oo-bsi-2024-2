package br.edu.ifal.eikefab.account;

import br.edu.ifal.eikefab.account.repository.AccountRepositoryImpl;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public final class AccountController {

    private static final AccountController INSTANCE = new AccountController();
    private static final AccountRepositoryImpl REPOSITORY = AccountRepositoryImpl.getInstance();

    private AccountController() {}

    public Set<Account> getAccounts() {
        return REPOSITORY.findAllAccounts();
    }

    public Set<Account> getAccountsByName(String name) {
        return REPOSITORY.findAllAccountsByName(name);
    }

    public Optional<Account> getAccountByUniqueId(UUID uniqueId) {
        return REPOSITORY.findAccountByUniqueId(uniqueId);
    }

    public void create(Account account) {
        REPOSITORY.createAccount(account);
    }

    public boolean exists(UUID id) {
        return REPOSITORY.findAccountByUniqueId(id).isPresent();
    }

    public void update(Account account) {
        REPOSITORY.updateAccount(account);
    }

    public void deleteByUniqueId(UUID id) {
        REPOSITORY.deleteAccount(id);
    }

    public boolean isConnected() {
        return REPOSITORY.isConnected();
    }

    public static AccountController getInstance() {
        return INSTANCE;
    }

}
