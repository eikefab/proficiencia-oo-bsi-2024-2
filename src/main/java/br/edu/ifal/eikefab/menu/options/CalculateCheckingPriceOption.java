package br.edu.ifal.eikefab.menu.options;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountController;
import br.edu.ifal.eikefab.account.AccountType;
import br.edu.ifal.eikefab.account.exceptions.AccountInvalidMonthException;
import br.edu.ifal.eikefab.account.impl.CheckingAccount;
import br.edu.ifal.eikefab.menu.MenuOption;
import br.edu.ifal.eikefab.menu.options.utils.AccountFinderUtils;

import java.util.UUID;

public class CalculateCheckingPriceOption extends MenuOption {

    public CalculateCheckingPriceOption() {
        super("Calcular preço mensal da conta-corrente", (scanner) -> {
            final Account account = AccountFinderUtils.getAccount(scanner, AccountType.CHECKING);

            System.out.println("Verificar preço após de (n. de meses) >");
            final int number = scanner.nextInt();

            if (number <= 0) {
                throw new AccountInvalidMonthException();
            }

            final CheckingAccount checkingAccount = (CheckingAccount) account;

            System.out
                    .format("Após %d meses, você terá R$ %s.",
                            number,
                            checkingAccount.getMaintenancePrice(number))
                    .println();
        });
    }

}
