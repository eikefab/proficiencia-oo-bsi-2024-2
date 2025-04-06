package br.edu.ifal.eikefab.menu.options.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException() {
        super("Conta não encontrada.");
    }

}
