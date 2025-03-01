package br.edu.ifal.eikefab.account.exception;

public class AccountNegativeTransactionException extends RuntimeException {

    public AccountNegativeTransactionException() {
        super("O valor a ser depositado deve ser positivo.");
    }

}
