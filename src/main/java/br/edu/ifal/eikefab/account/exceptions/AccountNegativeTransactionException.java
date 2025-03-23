package br.edu.ifal.eikefab.account.exceptions;

public class AccountNegativeTransactionException extends RuntimeException {

    public AccountNegativeTransactionException() {
        super("O valor a ser depositado deve ser positivo.");
    }

}
