package br.edu.ifal.eikefab.menu.options;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountController;
import br.edu.ifal.eikefab.menu.MenuOption;

import java.util.Set;

public class ListAccountOption extends MenuOption {

    public ListAccountOption() {
        super(
                1,
                "Visualizar contas",
                (_) -> {
                    Set<Account> accounts = AccountController.getInstance().getAccounts();

                    if (accounts.isEmpty()) {
                        System.out.println("Não há contas cadastradas no sistema.");

                        return;
                    }

                    System.out.format("Existem %d contas cadastradas no sistema...\n", accounts.size()).println();
                    System.out.println("ID\tTitular\tSaldo\tTipo\n");

                    for (Account account : accounts) {
                        System.out.
                                format(
                                        "%s\t%s\t%s\t%s",
                                        account.getUniqueId().toString(),
                                        account.getName(),
                                        "R$ " + account.getLocaleBalance(),
                                        account.getAccountType().getFancyName()
                                )
                                .println();
                    }
                }
        );
    }

}
