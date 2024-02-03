package losowyloch.project;

import java.util.ArrayList;

import losowyloch.project.entities.Player;

public class Main {
    public static void main(String[] args) {
        System.out.print("\033\143");
        System.out.print("\n\n");
        ArrayList<Player> players = new ArrayList<>();
        Menu menu = new Menu(players);
        menu.startGame();
    }
}