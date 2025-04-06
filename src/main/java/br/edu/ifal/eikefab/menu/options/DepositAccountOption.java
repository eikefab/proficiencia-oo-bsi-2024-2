package br.edu.ifal.eikefab.menu.options;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountController;
import br.edu.ifal.eikefab.account.exceptions.AccountNegativeTransactionException;
import br.edu.ifal.eikefab.menu.MenuOption;
import br.edu.ifal.eikefab.menu.options.utils.AccountFinderUtils;

public class DepositAccountOption extends MenuOption {

    public DepositAccountOption() {
        super("Realizar depósito", (scanner) -> {
            final Account account = AccountFinderUtils.getAccount(scanner);

            System.out.println("Valor a ser depositado >");
            final double value = scanner.nextDouble();

            if (value <= 0) {
                throw new AccountNegativeTransactionException();
            }

            account.deposit(value);
            AccountController.getInstance().update(account);

            System.out.println("Depósito realizado com sucesso.");
        });
    }

}
