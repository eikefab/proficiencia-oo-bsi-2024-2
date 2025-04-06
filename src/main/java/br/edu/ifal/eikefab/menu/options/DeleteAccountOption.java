package br.edu.ifal.eikefab.menu.options;

import br.edu.ifal.eikefab.account.AccountController;
import br.edu.ifal.eikefab.menu.MenuOption;

import java.util.UUID;

public class DeleteAccountOption extends MenuOption {

    public DeleteAccountOption() {
        super("Excluir conta pelo ID", (scanner) -> {
            final AccountController controller = AccountController.getInstance();

            System.out.println("Identificador da conta >");

            final String id = scanner.next();
            final UUID uniqueId = UUID.fromString(id);

            if (!controller.exists(uniqueId)) {
                System.out.println("A conta informada não existe.");

                return;
            }

            controller.deleteByUniqueId(uniqueId);

            System.out.println("Conta excluída.");
        });
    }
}
