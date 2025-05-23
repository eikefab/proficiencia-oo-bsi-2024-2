package br.edu.ifal.eikefab.account.impl;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountType;
import br.edu.ifal.eikefab.account.exceptions.AccountInvalidMonthException;
import java.util.UUID;

public class SavingsAccount extends Account {

    public static final double MONTH_GOV_INTEREST_RATE = 1.25;

    public SavingsAccount(UUID uniqueId, String name, String email, double balance) {
        super(uniqueId, name, email, balance);
    }

    public double balanceAfter(int months) {
        if (months <= 0) {
            throw new AccountInvalidMonthException();
        }

        final double rate = months * MONTH_GOV_INTEREST_RATE;
        final double balance = getBalance();

        return balance * rate;
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.SAVINGS;
    }

}
