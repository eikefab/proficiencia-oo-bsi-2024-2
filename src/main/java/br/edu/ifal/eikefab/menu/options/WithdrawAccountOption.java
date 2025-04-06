package br.edu.ifal.eikefab.menu.options;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountController;
import br.edu.ifal.eikefab.account.exceptions.AccountBalanceException;
import br.edu.ifal.eikefab.account.exceptions.AccountNegativeTransactionException;
import br.edu.ifal.eikefab.menu.MenuOption;
import br.edu.ifal.eikefab.menu.options.utils.AccountFinderUtils;

public class WithdrawAccountOption extends MenuOption {
    public WithdrawAccountOption() {
        super("Realizar saque", (scanner) -> {
            final Account account = AccountFinderUtils.getAccount(scanner);

            System.out.format("Valor a ser sacado (Disponível: R$ %s) >", account.getLocaleBalance()).println();
            final double value = scanner.nextDouble();

            if (value <= 0) {
                throw new AccountNegativeTransactionException();
            }

            if (value > account.getBalance()) {
                throw new AccountBalanceException();
            }

            account.withdraw(value);
            AccountController.getInstance().update(account);

            System.out.println("Saque realizado com sucesso.");
        });
    }
}
