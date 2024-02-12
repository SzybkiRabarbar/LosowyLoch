package losowyloch.project.managers;

import java.util.Random;

import losowyloch.project.UiHelper;
import losowyloch.project.entities.Player;
import losowyloch.project.skills.Skill;
import losowyloch.project.skills.creators.SkillCreator;

public class ShopManager {
    private UiHelper ui = new UiHelper();
    private Random random = new Random();
    private Player player;
    private int lvl;
    private SkillCreator skillCreator;
    private int[] skillsPrizes = new int[4];
    private Skill[] skills = new Skill[4];
    private String[] skillsMsgs = new String[5];

    ShopManager(Player player) {
        this.player = player;
        this.lvl = player.getLvl();
        this.generateSkills();
    }

    private void generateSkills() {
        this.skillCreator = new SkillCreator(lvl, 0);
        for (int i = 0; i < 4; i++) {
            this.skillsPrizes[i] = this.lvl + random.nextInt(this.lvl);
            this.skills[i] = skillCreator.create();
            this.skillsMsgs[i] = "\n(" + (i + 1) + ") Kup za " + this.skillsPrizes[i] + ": \n" + this.skills[i].getInfo();
        }
        this.skillsMsgs[4] = "\n(q) Wróc do sklepu";
    }

    private void noMoneyMsg() {
        System.out.println("Brak wystarczającej ilości złota!");
    }

    private void buySkill(int id) {
        if (player.subtractCurrency(this.skillsPrizes[id])) {
            player.addSkill(this.skills[id]);
            System.out.println("Kupiono umiejętność " + this.skills[id].getName() + "!\n");

            this.skillsPrizes[id] = this.lvl + random.nextInt(this.lvl);
            this.skills[id] = skillCreator.create();
            this.skillsMsgs[id] = "\n(" + (id + 1) + ") Kup za " + this.skillsPrizes[id] + ": \n" + this.skills[id].getInfo();
        } else {
            this.noMoneyMsg();
        }
    }

    private void skillAisle() {
        System.out.println("Kup umiejętności!");
        while (true) {
            System.out.println("Złoto: " + player.getCurrency());
            char input = this.ui.showAndCollectInput(skillsMsgs, "1234q".toCharArray());
            if (input == 'q') {break;}
            int index = (int) (input - '0') - 1;
            this.buySkill(index);
        }
    }
        
    private void modsAisle() {
        System.out.println("Kup przedmioty!");
        System.out.println("Złoto: " + player.getCurrency() + "\n");

    }

    private void buyStat() {
        if (this.player.subtractCurrency(this.player.getPointPrize())) {
            this.player.addStat();
            this.player.addPointPrize();
        } else {
            this.noMoneyMsg();
        }
    }

    private void statAisle() {
        System.out.println("Kup statystyki!");
        System.out.println("Złoto: " + player.getCurrency() + "\n");
        String[] msgs = new String[] {
            "",
            "(q) Wróć do sklepu",
        };
        boolean isLoop = true;
        while (isLoop) {
            msgs[0] = "(a) Kup statysyke za " + this.player.getPointPrize();
            char input = this.ui.showAndCollectInput(msgs, "aq".toCharArray());
            switch (input) {
                case 'a':
                    this.buyStat();
                    break;
                case 'q':
                    isLoop = false;
                    break;
                default:
                    break;
            }
        }
    }

    public void shopLoop() {
        System.out.println("Sklep poziomu " + (player.getLvl() - 2) + "!\n");
        String[] msgs = new String[] {
            "Wybierz co cię interesuje:",
            "(a) Umiejętności",
            "(s) Przedmioty",
            "(d) Punkty statystyk",
            "(f) Zobacz informacje o swojej postaci",
            "(q) Wyjdź ze sklepu"
        };
        boolean isLoop = true;
        while (isLoop) {
            char input = this.ui.showAndCollectInput(msgs, "asdfq".toCharArray());
            switch (input) {
                case 'a':
                    this.skillAisle();
                    break;
                case 's':
                    this.modsAisle();
                    break;
                case 'd':
                    this.statAisle();
                    break;
                case 'f':
                    this.player.showInfo();
                    break;
                case 'q':
                    isLoop = false;
                    break;
                default:
                    break;
            }
        }
    }
}
