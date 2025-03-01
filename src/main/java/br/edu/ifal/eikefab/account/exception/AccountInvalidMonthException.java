package br.edu.ifal.eikefab.account.exception;

public class AccountInvalidMonthException extends RuntimeException {

    public AccountInvalidMonthException() {
        super("É necessário que 'meses' seja positivo.");
    }

}
