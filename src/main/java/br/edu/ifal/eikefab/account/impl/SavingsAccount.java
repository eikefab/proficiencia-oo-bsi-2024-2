package br.edu.ifal.eikefab.account.impl;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.exception.AccountInvalidMonthException;
import br.edu.ifal.eikefab.transaction.Transaction;

import java.util.List;
import java.util.UUID;

public class SavingsAccount extends Account {

    public static final double MONTH_GOV_INTEREST_RATE = 1.25;

    public SavingsAccount(UUID uniqueId, String name, String email, List<Transaction> transactions, double balance) {
        super(uniqueId, name, email, transactions, balance);
    }

    public double calculateSavingsAfter(int months) {
        if (months <= 0) {
            throw new AccountInvalidMonthException();
        }

        final double rate = months * MONTH_GOV_INTEREST_RATE;
        final double balance = getBalance();

        return balance * rate;
    }

}
