package br.edu.ifal.eikefab.transaction.exceptions;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountType;

public class IllegalTransactionException extends RuntimeException {

    public IllegalTransactionException(Account from) {
        super("Transação inválida, ambas as contas devem ser " + from.getAccountType().name() + ".");
    }

}
