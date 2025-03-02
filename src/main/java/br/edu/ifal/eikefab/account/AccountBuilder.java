package br.edu.ifal.eikefab.account;

import br.edu.ifal.eikefab.transaction.Transaction;

import java.util.List;
import java.util.UUID;

public final class AccountBuilder {

    private UUID uniqueId;
    private String name;
    private String email;
    private double balance;
    private List<Transaction> transactions;

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

    public AccountBuilder transactions(List<Transaction> transactions) {
        this.transactions = transactions;

        return this;
    }

    public SavingsAccount savings() {
        return new SavingsAccount(uniqueId, name, email, transactions, balance);
    }

    public CheckingAccount checking() {
        return new CheckingAccount(uniqueId, name, email, transactions, balance);
    }

}
