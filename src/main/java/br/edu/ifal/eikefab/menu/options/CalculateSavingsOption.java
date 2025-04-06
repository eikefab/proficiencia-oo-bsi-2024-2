package br.edu.ifal.eikefab.menu.options;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountType;
import br.edu.ifal.eikefab.account.exceptions.AccountInvalidMonthException;
import br.edu.ifal.eikefab.account.impl.SavingsAccount;
import br.edu.ifal.eikefab.menu.MenuOption;
import br.edu.ifal.eikefab.menu.options.utils.AccountFinderUtils;

public class CalculateSavingsOption extends MenuOption {

    public CalculateSavingsOption() {
        super("Calcular rendimentos da poupança", (scanner) -> {
            final Account account = AccountFinderUtils.getAccount(scanner, AccountType.SAVINGS);

            System.out.println("Verificar rendimento depois de (n. de meses) >");
            final int number = scanner.nextInt();

            if (number <= 0) {
                throw new AccountInvalidMonthException();
            }

            final SavingsAccount savingsAccount = (SavingsAccount) account;

            System.out
                    .format("Após %d meses, você terá R$ %s.",
                        number,
                        Account.DECIMAL_FORMAT.format(savingsAccount.balanceAfter(number)))
                    .println();
        });
    }

}
