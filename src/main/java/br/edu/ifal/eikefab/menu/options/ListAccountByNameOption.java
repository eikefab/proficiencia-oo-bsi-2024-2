package br.edu.ifal.eikefab.menu.options;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountController;
import br.edu.ifal.eikefab.menu.MenuOption;

import java.util.Set;

public class ListAccountByNameOption extends MenuOption {

    public ListAccountByNameOption() {
        super("Buscar contas pelo nome", (scanner) -> {
            System.out.println("Nome a ser buscado >");
            scanner.nextLine();

            final String queryName = scanner.nextLine();
            final Set<Account> matchAccounts = AccountController.getInstance().getAccountsByName(queryName);

            if (matchAccounts.isEmpty()) {
                System.out.format("Nenhum resultado encontrado para %s", queryName);

                return;
            }

            System.out.format("%d resultados encontrados para %s", matchAccounts.size(), queryName).println();
            System.out.println("ID\tTitular\tSaldo\tTipo\n");

            for (Account account : matchAccounts) {
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
        });
    }

}
