package losowyloch.project.managers;

import java.util.ArrayList;

import losowyloch.project.PlayerJsonUtil;
import losowyloch.project.UiHelper;
import losowyloch.project.entities.Enemy;
import losowyloch.project.entities.Mod;
import losowyloch.project.entities.Player;
import losowyloch.project.entities.creators.EnemyCreator;

public class GameManager {
    private UiHelper ui = new UiHelper();
    private EnemyCreator enemyCreator = new EnemyCreator();
    private Player player;
    public GameManager(Player player) {
        this.player = player;
    }

    private void showInfoAboutEnemy(Enemy enemy) {
        System.out.println(enemy.getName());
        System.out.println("\n* Statystyki:");
        enemy.getStatsInfo(true);
        System.out.println("\n* Umiejętności:");
        enemy.getSkillsInfo(true, true);
        System.out.println("\n");
    }

    private Enemy announceAndGetEnemy(){
        Enemy enemy = enemyCreator.create(this.player.getLvl(), this.player.getGlory()); 
        System.out.println("Następny przeciwnik: " + enemy.getName() + "\n");
        return enemy;
    }

    public void mainLoop() {
        System.out.println("Rozpoczęcie rozgrywki jako " + this.player.getName() + "\n");
        Enemy enemy = this.announceAndGetEnemy();
        boolean isRunnig = true;
        while (isRunnig) {
            String[] loopMsg = new String[] {
                "Wybierz co chcesz zrobić:",
                "(a) Walcz z " + enemy.getName(),
                "(s) Zobacz statystyki następnego przeciwnika",
                "(d) Zwiększ chwałe o " + this.player.getLvl(),
                "(f) Zobacz swoje statystyki",
                "(q) Wyjdź z programu",
            };
            char input = ui.showAndCollectInput(loopMsg, "asdfq".toCharArray());
            switch (input) {
                case 'a':
                    System.out.println("Walka z " + enemy.getName() + "\n");
                    FightManager fight = new FightManager(this.player, enemy);
                    boolean playerWin = fight.fightLoop();
                    if (playerWin) {
                        ArrayList<Mod> used = this.player.useModsDurability();
                        for (Mod mod : used) {
                            System.out.println("Przedmiot " + mod.getName() + " został zużyty!\n");
                        }
                        ShopManager shop = new ShopManager(this.player);
                        shop.shopLoop();
                        enemy = announceAndGetEnemy();
                        PlayerJsonUtil.replacePlayerInJsonFile(this.player);
                    } else {
                        PlayerJsonUtil.removePlayerFromJsonFile(this.player.getId());
                        System.out.println("Koniec gry!\n");
                        isRunnig = false;
                    }
                    break;
                case 's':
                    System.out.println("Informacje o następnym przeciwniku\n");
                    this.showInfoAboutEnemy(enemy);
                    break;
                case 'd':
                    this.player.addGlory(this.player.getLvl());
                    System.out.println("Dodano " + this.player.getLvl() + " chwały\nAktualnie posiadasz "
                                       + this.player.getGlory() + " chwały\n");
                    PlayerJsonUtil.replacePlayerInJsonFile(this.player);
                    enemy = this.announceAndGetEnemy();
                    break;
                case 'f':
                    System.out.println("Informacje o postaci\n");
                    this.player.showInfo();
                    break;
                case 'q':
                    isRunnig = false;
                    break;
                default:
                    break;
            }
        }
    }
}
