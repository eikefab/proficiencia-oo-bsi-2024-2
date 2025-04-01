package br.edu.ifal.eikefab.menu;

public class MenuOption {

    private final int number;
    private final String text;
    private final MenuHandler handler;

    public MenuOption(int number, String text, MenuHandler handler) {
        this.number = number;
        this.text = text;
        this.handler = handler;
    }

    public int getNumber() {
        return number;
    }

    public String getText() {
        return text;
    }

    public MenuHandler getHandler() {
        return handler;
    }

}
