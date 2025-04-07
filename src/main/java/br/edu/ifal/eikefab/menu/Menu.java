package br.edu.ifal.eikefab.menu;

import br.edu.ifal.eikefab.menu.options.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public final class Menu {

    private static final List<MenuOption> OPTIONS = new ArrayList<>(
            Arrays.asList(
                    new ListAccountOption(),
                    new ListAccountByNameOption(),
                    new CreateAccountOption(),
                    new DeleteAccountOption(),
                    new CheckBalanceOption(),
                    new WithdrawAccountOption(),
                    new DepositAccountOption(),
                    new CalculateSavingsOption(),
                    new CalculateCheckingPriceOption()
            )
    );

    public static void printOptions() {
        for (int index = 0; index < OPTIONS.size(); index++) {
            final MenuOption option = OPTIONS.get(index);

            System.out.format("%d - %s", (index + 1), option.getText()).println();
        }

        System.out.format("\nOpção (1-%d) > ", OPTIONS.size()).println();
    }

    public static void printHeader() {
        System.out.println("Bem-vindo(a)! Como posso lhe ajudar?");
    }

    public static void print() {
        printHeader();
        printOptions();
    }

    public static void init() {
        print();

        try (Scanner scanner = new Scanner(System.in)) {
            int optionIndex = scanner.nextInt();

            while (optionIndex != 0 && optionIndex <= OPTIONS.size()) {
                final MenuOption option = OPTIONS.get(optionIndex - 1);

                System.out.println();

                try {
                    option.getHandler().onSelect(scanner);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

                System.out.println();

                printOptions();

                optionIndex = scanner.nextInt();
            }
        }
    }

}
