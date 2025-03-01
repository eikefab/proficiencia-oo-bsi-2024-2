package br.edu.ifal.eikefab.account.impl;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.exception.AccountInvalidMonthException;
import br.edu.ifal.eikefab.transaction.Transaction;

import java.util.List;
import java.util.UUID;

public class CheckingAccount extends Account {

    public static final double CHECKING_ACCOUNT_MONTH_MAINTENANCE_PRICE = 5;

    public CheckingAccount(UUID uniqueId, String name, String email, List<Transaction> transactions, double balance) {
        super(uniqueId, name, email, transactions, balance);
    }

    public double getMaintenancePrice(int months) {
        if (months <= 0) {
            throw new AccountInvalidMonthException();
        }

        return months * CHECKING_ACCOUNT_MONTH_MAINTENANCE_PRICE;
    }

}
