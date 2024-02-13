package losowyloch.project.managers;

import java.util.List;

import losowyloch.project.PlayerJsonUtil;
import losowyloch.project.UiHelper;
import losowyloch.project.entities.Player;
import losowyloch.project.entities.creators.PlayerCreator;

public class MenuManager {
    private UiHelper ui;
    private GameManager gameManager;

    public MenuManager() {
        this.ui = new UiHelper();
    }

    public void startGame() {
        String[] msgs = {
            "Wybierz co chcesz zrobić",
            "(1) Pokaż liste postaci.",
            "(2) Wybierz postać.",
            "(3) Stwórz postać.",
            "(4) Pomoc.",
            "(q) Wyjdź z lochu.",
            "Wypisz cyfre od 1 do 5:",
        };
        char[] validInputs = {'1', '2', '3', '4', 'q'};
        List<Player> players;
        boolean looped = true;
        while (looped) {
            char input = ui.showAndCollectInput(msgs, validInputs);
            switch (input) {
                case '1':
                    players = PlayerJsonUtil.fetchPlayersFromJson();
                    for (Player player : players) {
                        System.out.println("*** *** *** *** ***");
                        player.showInfo();
                    }
                    break;
                case '2':
                    players = PlayerJsonUtil.fetchPlayersFromJson();
                    System.out.println("Wybierz postać z listy:");
                    for (int i = 0; i < players.size(); i++) {
                        System.out.println("(" + (i + 1) + ") " + (players.get(i).getLvl() - 2) + " lvl | " + players.get(i).getName());
                    }
                    System.out.println("( ) Powrót");
                    int id = -1;
                    try {
                        id = Integer.parseInt(ui.getScanner().nextLine()) - 1;
                    } catch (NumberFormatException e) {
                        break;
                    }
                    if (0 <= id && id < players.size()) {
                        this.gameManager = new GameManager(players.get(id));
                        this.gameManager.mainLoop();
                    }
                    break;
                case '3':
                    System.out.println("Tworzenie postaci!\n");
                    PlayerCreator playerCreator = new PlayerCreator();
                    Player player = playerCreator.create();
                    PlayerJsonUtil.addPlayerToJsonFile(player);
                    this.gameManager = new GameManager(player);
                    this.gameManager.mainLoop();
                    break;
                case '4':
                    // TODO
                    break;
                case 'q':
                    looped = false;
                    break;
                default:
                    break;
            }
        }
    }
}
