package losowyloch.project;

import losowyloch.project.managers.MenuManager;

public class Main {
    public static void main(String[] args) {
        System.out.print("\033\143");
        System.out.print("\n\n");
        MenuManager menu = new MenuManager();
        menu.startGame();
    }
}