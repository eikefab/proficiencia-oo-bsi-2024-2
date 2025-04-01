package br.edu.ifal.eikefab.menu;

import br.edu.ifal.eikefab.menu.options.CheckBalanceOption;
import br.edu.ifal.eikefab.menu.options.ListAccountOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public final class Menu {

    private static final List<MenuOption> OPTIONS = new ArrayList<>(
            Arrays.asList(
                    new ListAccountOption(),
                    // CREATE ACCOUNT OPTION
                    // DELETE ACCOUNT OPTION
                    // WITHDRAW ACCOUNT OPTION
                    // DEPOSIT ACCOUNT OPTION
                    // CALCULATE SAVINGS OPTION
                    // CALCULATE CHECKING PRICE OPTION
                    new CheckBalanceOption()
            )
    );

    public static void printOptions() {
        for (MenuOption option : OPTIONS) {
            System.out.format("%d\t%s", option.getNumber(), option.getText()).println();
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

                option.getHandler().onSelect(scanner);

                System.out.println();

                printOptions();

                optionIndex = scanner.nextInt();
            }
        }
    }

}
