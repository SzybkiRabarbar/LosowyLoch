package losowyloch.project.entities.creators;

import losowyloch.project.entities.Player;
import losowyloch.project.skills.Skill;
import losowyloch.project.skills.creators.SkillCreator;

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

    private void addSkills(Player player) {
        SkillCreator skillCreator = new SkillCreator(player.getLvl(), 0);
        System.out.println("Wybór umiejętności!\n");
        Skill[] currSkills = new Skill[6];
        String[] currMsgs = new String[6];

        for (int i = 0; i < 3; i++) {
            int showNumber = i + 1;
            System.out.println("Wybierz " + showNumber + " z 3 umiejętności z listy:\n");

            for (int j = 0; j < 6; j++) {
                Skill currSkill = skillCreator.create();
                currSkills[j] = currSkill;
                currMsgs[j] = "\n(" + j + ") " + currSkill.getInfo();
            }
            char input = ui.showAndCollectInput(currMsgs, "012345".toCharArray());
            Skill pickedSkill = currSkills[input - '0'];
            System.out.println("Wybrałeś " + pickedSkill.getName() + "!\n");

            player.addSkill(pickedSkill);
        }
    }

    public Player create() { 
        System.out.println("Wybierz imię dla swojej postaci");
        String name = this.pickName();
        System.out.println("Wybrane Imie to " + name + "!\n");

        Player player = new Player(name, new int[]{1,1,1,1,1,1,1}, 0);

        this.addPoints(player);
        this.addSkills(player);
        return player;
    }
}
