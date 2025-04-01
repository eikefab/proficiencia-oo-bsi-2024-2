package br.edu.ifal.eikefab.account.impl;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountType;
import br.edu.ifal.eikefab.account.exceptions.AccountInvalidMonthException;

import java.util.UUID;

public class CheckingAccount extends Account {

    public static final double CHECKING_ACCOUNT_MONTH_MAINTENANCE_PRICE = 5;

    public CheckingAccount(UUID uniqueId, String name, String email, double balance) {
        super(uniqueId, name, email, balance);
    }

    public double getMaintenancePrice(int months) {
        if (months <= 0) {
            throw new AccountInvalidMonthException();
        }

        return months * CHECKING_ACCOUNT_MONTH_MAINTENANCE_PRICE;
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.CHECKING;
    }

}
