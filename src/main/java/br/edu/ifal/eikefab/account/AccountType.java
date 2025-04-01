package br.edu.ifal.eikefab.account;

public enum AccountType {

    SAVINGS("POUPANÇA"),
    CHECKING("CORRENTE");

    private final String fancyName;

    AccountType(String fancyName) {
        this.fancyName = fancyName;
    }

    public String getFancyName() {
        return fancyName;
    }

}
