package br.edu.ifal.eikefab.account.exceptions;

public class AccountBalanceException extends RuntimeException {

    public AccountBalanceException() {
        super("A conta não pode ter saldo negativo.");
    }

}
