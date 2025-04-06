package br.edu.ifal.eikefab.menu.options.utils;

import br.edu.ifal.eikefab.account.Account;
import br.edu.ifal.eikefab.account.AccountController;
import br.edu.ifal.eikefab.account.AccountType;
import br.edu.ifal.eikefab.menu.options.exception.AccountNotFoundException;
import br.edu.ifal.eikefab.menu.options.exception.UnsupportedAccountTypeException;

import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public final class AccountFinderUtils {

    public static Account getAccount(Scanner scanner) {
        System.out.println("Identificador da conta >");

        final String id = scanner.next();
        final UUID uniqueId = UUID.fromString(id);

        final AccountController controller = AccountController.getInstance();
        final Optional<Account> account = controller.getAccountByUniqueId(uniqueId);

        if (account.isEmpty()) {
            throw new AccountNotFoundException();
        }

        return account.get();
    }

    public static Account getAccount(Scanner scanner, AccountType type) {
        final Account account = getAccount(scanner);

        if (account.getAccountType() != type) {
            throw new UnsupportedAccountTypeException(type);
        }

        return account;
    }

}
