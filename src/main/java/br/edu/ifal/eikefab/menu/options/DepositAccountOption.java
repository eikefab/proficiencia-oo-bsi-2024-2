package br.edu.ifal.eikefab.menu.options;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountController;
import br.edu.ifal.eikefab.menu.MenuOption;

import java.util.UUID;

public class DepositAccountOption extends MenuOption {

    public DepositAccountOption() {
        super("Realizar depósito", (scanner) -> {
            final AccountController controller = AccountController.getInstance();

            System.out.println("Identificador da conta >");

            final String id = scanner.next();
            final UUID uniqueId = UUID.fromString(id);

            if (!controller.exists(uniqueId)) {
                System.out.println("A conta informada não existe.");

                return;
            }

            final Account account = controller.getAccountByUniqueId(uniqueId).get();

            System.out.println("Valor a ser depositado >");
            final double value = scanner.nextDouble();

            if (value <= 0) {
                System.out.println("Valor inválido!");

                return;
            }

            account.deposit(value);
            controller.update(account);

            System.out.println("Depósito realizado com sucesso.");
        });
    }

}
