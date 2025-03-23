package br.edu.ifal.eikefab.transaction;

import br.edu.ifal.eikefab.account.Account;

public final class TransactionValidator {

    private TransactionValidator() {}

    public static boolean isValid(Transaction transaction) {
        final Account from = transaction.getFrom();
        final Account to = transaction.getTo();
        final double transactionValue = 0;

        return false;
    }

}
