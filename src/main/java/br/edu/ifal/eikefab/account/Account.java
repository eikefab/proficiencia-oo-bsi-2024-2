package br.edu.ifal.eikefab.account;

import br.edu.ifal.eikefab.account.exception.AccountBalanceException;
import br.edu.ifal.eikefab.account.exception.AccountNegativeTransactionException;
import br.edu.ifal.eikefab.transaction.Transaction;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public abstract class Account {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###.###");

    private final UUID uniqueId;
    private final String name;
    private final String email;
    private final List<Transaction> transactions;
    private double balance;

    protected Account(UUID uniqueId, String name, String email, List<Transaction> transactions, double balance) {
        this.uniqueId = Objects.requireNonNull(uniqueId);
        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(email);
        this.transactions = Objects.requireNonNull(transactions);

        if (balance < 0) {
            throw new AccountBalanceException();
        }

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

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions); // Retorna cópia da lista de transações
    }

    protected void setBalance(double balance) {
        if (balance < 0) {
            throw new AccountBalanceException();
        }

        this.balance = balance;
    }

    protected void deposit(double balance) {
        if (balance < 0) {
            throw new AccountNegativeTransactionException();
        }

        this.balance = balance;
    }

    protected void withdraw(double balance) {
        if (balance < 0) {
            throw new AccountNegativeTransactionException();
        }

        final double nextBalance = getBalance() - balance;

        if (nextBalance < 0) {
            throw new AccountBalanceException();
        }

        this.balance = nextBalance;
    }

    protected void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public abstract AccountType getAccountType();

    public static AccountBuilder builder() {
        return new AccountBuilder();
    }

}
