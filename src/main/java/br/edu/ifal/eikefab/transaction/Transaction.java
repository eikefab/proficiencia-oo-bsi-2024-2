package br.edu.ifal.eikefab.transaction;

import br.edu.ifal.eikefab.account.Account;

import java.util.Objects;
import java.util.UUID;

public class Transaction {

    private final UUID uniqueId;
    private final Account from;
    private final Account to;
    private final long millis;
    private final boolean valid;
    private final double value;

    public Transaction(UUID uniqueId, Account from, Account to, long millis, boolean valid, double value) {
        this.uniqueId = Objects.requireNonNull(uniqueId);
        this.from = Objects.requireNonNull(from);
        this.to = Objects.requireNonNull(to);
        this.millis = millis;
        this.valid = valid;
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

    public boolean isValid() {
        return valid;
    }

    public double getValue() {
        return value;
    }

}
