package br.edu.ifal.eikefab.menu.options;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountController;
import br.edu.ifal.eikefab.menu.MenuOption;

import java.util.UUID;

public class WithdrawAccountOption extends MenuOption {
    public WithdrawAccountOption() {
        super("Realizar saque", (scanner) -> {
            final AccountController controller = AccountController.getInstance();

            System.out.println("Identificador da conta >");

            final String id = scanner.next();
            final UUID uniqueId = UUID.fromString(id);

            if (!controller.exists(uniqueId)) {
                System.out.println("A conta informada não existe.");

                return;
            }

            final Account account = controller.getAccountByUniqueId(uniqueId).get();

            System.out.format("Valor a ser sacado (Disponível: R$ %s) >", account.getLocaleBalance()).println();
            final double value = scanner.nextDouble();

            if (value <= 0 || value > account.getBalance()) {
                System.out.println("Valor inválido!");

                return;
            }

            account.withdraw(value);
            controller.update(account);

            System.out.println("Saque realizado com sucesso.");
        });
    }
}
