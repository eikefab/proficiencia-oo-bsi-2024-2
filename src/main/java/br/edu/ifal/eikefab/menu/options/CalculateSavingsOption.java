package br.edu.ifal.eikefab.menu.options;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountController;
import br.edu.ifal.eikefab.account.AccountType;
import br.edu.ifal.eikefab.account.impl.SavingsAccount;
import br.edu.ifal.eikefab.menu.MenuOption;

import java.util.UUID;

public class CalculateSavingsOption extends MenuOption {

    public CalculateSavingsOption() {
        super("Calcular rendimentos da poupança", (scanner) -> {
            final AccountController controller = AccountController.getInstance();

            System.out.println("Identificador da conta >");

            final String id = scanner.next();
            final UUID uniqueId = UUID.fromString(id);

            if (!controller.exists(uniqueId)) {
                System.out.println("A conta informada não existe.");

                return;
            }

            final Account account = controller.getAccountByUniqueId(uniqueId).get();

            if (account.getAccountType() != AccountType.SAVINGS) {
                System.out.println("O tipo da conta deve ser poupança.");

                return;
            }

            System.out.println("Verificar rendimento depois de (n. de meses) >");
            final int number = scanner.nextInt();

            if (number <= 0) {
                System.out.println("Mês inválido.");

                return;
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
