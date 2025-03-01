package br.edu.ifal.eikefab.transaction;

public final class TransactionController {

    private static final TransactionController INSTANCE = new TransactionController();

    private TransactionController() {}

    public static TransactionController getInstance() {
        return INSTANCE;
    }

}
