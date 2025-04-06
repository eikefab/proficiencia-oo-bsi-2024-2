package br.edu.ifal.eikefab.menu.options;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountBuilder;
import br.edu.ifal.eikefab.account.AccountController;
import br.edu.ifal.eikefab.menu.MenuOption;

import java.util.UUID;

public class CreateAccountOption extends MenuOption {

    public CreateAccountOption() {
        super("Criar conta", (scanner) -> {
            System.out.println("Titular da conta >");
            scanner.nextLine();

            final String name = scanner.nextLine();

            System.out.println("E-mail do titular da conta >");
            final String email = scanner.nextLine();

            System.out.println("Tipo da conta (1 - Poupança, 2 - Corrente) >");
            int option = scanner.nextInt();

            while (option != 1 && option != 2) {
                System.out.println("Opção inválida!");
                System.out.println("Tipo da conta (1 - Poupança, 2 - Corrente) >");

                option = scanner.nextInt();
            }

            final AccountBuilder builder = Account.builder()
                    .uniqueId(UUID.randomUUID())
                    .name(name)
                    .email(email)
                    .balance(0);

            final Account account;

            if (option == 1) {
                account = builder.savings();
            } else {
                account = builder.checking();
            }

            AccountController.getInstance().create(account);

            System.out.println("Conta criada com sucesso.");
        });
    }

}
