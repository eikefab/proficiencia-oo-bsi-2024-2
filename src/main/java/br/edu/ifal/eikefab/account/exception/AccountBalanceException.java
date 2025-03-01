package br.edu.ifal.eikefab.account.exception;

public class AccountBalanceException extends RuntimeException {

    public AccountBalanceException() {
        super("A conta não pode ter saldo negativo.");
    }

}
