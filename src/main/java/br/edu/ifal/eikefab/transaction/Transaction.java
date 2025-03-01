package br.edu.ifal.eikefab.transaction;

import br.edu.ifal.eikefab.account.Account;

import java.util.UUID;

public class Transaction {

    private final UUID uniqueId;
    private final Account from;
    private final Account to;
    private final long millis;
    private final boolean success;
    private final double value;

    protected Transaction(UUID uniqueId, Account from, Account to, long millis, boolean success, double value) {
        this.uniqueId = uniqueId;
        this.from = from;
        this.to = to;
        this.millis = millis;
        this.success = success;
        this.value = value;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public Account getFrom() {
        return from;
    }

    public Account getTo() {
        return to;
    }

    public long getMillis() {
        return millis;
    }

    public boolean isSuccess() {
        return success;
    }

    public double getValue() {
        return value;
    }

}
