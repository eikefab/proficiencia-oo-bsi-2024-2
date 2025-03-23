package br.edu.ifal.eikefab.transaction.exceptions;

import br.edu.ifal.eikefab.account.Account;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(Account from, double transactionValue) {
        super(
                from.getName() +
                        " não possui o valor necessário (R$ " +
                        Account.DECIMAL_FORMAT.format(transactionValue) +
                        ") para completar a transação."
        );
    }

}
