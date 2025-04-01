package br.edu.ifal.eikefab.account;

import br.edu.ifal.eikefab.account.exceptions.AccountBalanceException;
import br.edu.ifal.eikefab.account.exceptions.AccountNegativeTransactionException;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.UUID;

public abstract class Account {

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###.###");

    private final UUID uniqueId;
    private final String name;
    private final String email;
    private double balance;

    protected Account(UUID uniqueId, String name, String email, double balance) {
        this.uniqueId = Objects.requireNonNull(uniqueId);
        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(email);
        this.balance = balance;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public double getBalance() {
        return balance;
    }

    public String getLocaleBalance() {
        return DECIMAL_FORMAT.format(balance);
    }

    public void deposit(double balance) {
        if (balance < 0) {
            throw new AccountNegativeTransactionException();
        }

        this.balance += balance;
    }

    public void withdraw(double balance) {
        if (balance < 0) {
            throw new AccountNegativeTransactionException();
        }

        final double nextBalance = getBalance() - balance;

        if (nextBalance < 0) {
            throw new AccountBalanceException();
        }

        this.balance = nextBalance;
    }

    public abstract AccountType getAccountType();

    public static AccountBuilder builder() {
        return new AccountBuilder();
    }

}
