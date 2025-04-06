package br.edu.ifal.eikefab.menu.options.exception;

public class NoAccountsException extends RuntimeException {

    public NoAccountsException() {
        super("Não há contas cadastradas sob os filtros utilizados.");
    }

}
