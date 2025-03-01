package br.edu.ifal.eikefab.account;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class AccountController {

    private final AccountRepository repository;

    public AccountController(AccountRepository repository) {
        this.repository = repository;

        repository.connect();
    }

    public Set<Account> getAccounts() {
        return repository.findAllAccounts();
    }

    public Set<Account> getAccountsByName(String name) {
        return repository.findAllAccountsByName(name);
    }

    public Optional<Account> getAccountByUniqueId(UUID uniqueId) {
        return repository.findAccountByUniqueId(uniqueId);
    }

    public void transaction(Account from, Account to, double balance) {
        // TODO: impl transaction controller
    }

    public void create(Account account) {
        repository.createAccount(account);
    }

    public void stop() {
        repository.disconnect();
    }

}
