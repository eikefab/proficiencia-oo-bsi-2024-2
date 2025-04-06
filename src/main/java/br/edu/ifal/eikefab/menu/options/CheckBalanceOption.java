package br.edu.ifal.eikefab.menu.options;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.menu.MenuOption;
import br.edu.ifal.eikefab.menu.options.utils.AccountFinderUtils;

public class CheckBalanceOption extends MenuOption {

    public CheckBalanceOption() {
        super("Verificar saldo da conta", (scanner) -> {
            final Account account = AccountFinderUtils.getAccount(scanner);

            System.out.format("%s possui R$ %s", account.getName(), account.getLocaleBalance()).println();
        });
    }

}
