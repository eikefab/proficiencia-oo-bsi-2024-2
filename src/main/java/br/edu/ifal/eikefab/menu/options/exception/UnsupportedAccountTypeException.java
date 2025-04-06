package br.edu.ifal.eikefab.menu.options.exception;

import br.edu.ifal.eikefab.account.AccountType;

public class UnsupportedAccountTypeException extends RuntimeException {

    public UnsupportedAccountTypeException(AccountType accountType) {
        super("A conta deve ser do tipo " + accountType.getFancyName() + " para prosseguir.");
    }

}
