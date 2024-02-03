package losowyloch.project.entities.creators;

import losowyloch.project.entities.Player;
import losowyloch.project.RandomWordPicker;
import losowyloch.project.UiHelper;

public class PlayerCreator {
    private UiHelper ui;
    public PlayerCreator() {
        this.ui = new UiHelper();
    }

    private String pickName() {
        String[] names = new String[10];
        String[] msgs = new String[10];
        for (int i = 0; i < 10; i++) {
            String[] adjectiveWithNoun = RandomWordPicker.getRandomWords('r');
            names[i] = adjectiveWithNoun[0] + " " + adjectiveWithNoun[1];
            msgs[i] = "(" + i + ") " + adjectiveWithNoun[0] + " " + adjectiveWithNoun[1];
        }
        char input = ui.showAndCollectInput(msgs, "0123456789".toCharArray()); 
        int picked = input - '0';
        return names[picked];
    }

    private void addPoints(Player player){
        for (int i = 6; i > 0; i--) {
            System.err.println("Liczba punktów do rozdania: " + i + "\n");
            player.addStat();
        }
    }

    public Player create() { 
        System.out.println("Wybierz imię dla swojej postaci");;
        String name = this.pickName();
        System.out.println("Wybrane Imie to " + name + "!\n");
        Player player = new Player(name, new int[]{1,1,1,1,1,1,1}, 0);
        // TODO dodawanie skilli
        this.addPoints(player);
        return player;
    }
}
