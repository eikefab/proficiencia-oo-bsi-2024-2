package br.edu.ifal.eikefab.menu;

public class MenuOption {

    private final String text;
    private final MenuHandler handler;

    public MenuOption(String text, MenuHandler handler) {
        this.text = text;
        this.handler = handler;
    }

    public String getText() {
        return text;
    }

    public MenuHandler getHandler() {
        return handler;
    }

}
