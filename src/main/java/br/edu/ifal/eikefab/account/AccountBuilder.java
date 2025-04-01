package br.edu.ifal.eikefab.account;

import br.edu.ifal.eikefab.account.impl.CheckingAccount;
import br.edu.ifal.eikefab.account.impl.SavingsAccount;

import java.util.UUID;

public final class AccountBuilder {

    private UUID uniqueId;
    private String name;
    private String email;
    private double balance;

    public AccountBuilder uniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;

        return this;
    }

    public AccountBuilder name(String name) {
        this.name = name;

        return this;
    }

    public AccountBuilder email(String email) {
        this.email = email;

        return this;
    }

    public AccountBuilder balance(double balance) {
        this.balance = balance;

        return this;
    }

    public SavingsAccount savings() {
        return new SavingsAccount(uniqueId, name, email, balance);
    }

    public CheckingAccount checking() {
        return new CheckingAccount(uniqueId, name, email, balance);
    }

}
