package losowyloch.project.managers;

import java.util.ArrayList;

import losowyloch.project.UiHelper;
import losowyloch.project.entities.Player;
import losowyloch.project.entities.creators.PlayerCreator;

public class MenuManager {
    private ArrayList<Player> players;
    private UiHelper ui;

    public MenuManager(ArrayList<Player> players) {
        this.players = players;
        this.ui = new UiHelper();
    }

    public void startGame() {
        String[] msgs = {
            "Wybierz co chcesz zrobić",
            "(1) Pokaż liste postaci.",
            "(2) Stwórz postać.",
            "(3) Usuń postać.",
            "(4) Pomoc.",
            "Wypisz cyfre od 1 do 4:",
        };
        char[] validInputs = {'1', '2', '3', '4'};
        char input = ui.showAndCollectInput(msgs, validInputs);
        switch (input) {
            case '1':
                // TODO lista postaci z json
                break;
            case '2':
                System.out.println("Tworzenie postaci!\n");
                PlayerCreator playerCreator = new PlayerCreator();
                Player player = playerCreator.create();
                players.add(player);
                GameManager gameManager = new GameManager(player);
                gameManager.mainLoop();
                break;
            case '3':
                // TODO
                break;
            case '4':
                // TODO
                break;
            default:
                break;
        }
    }
}
