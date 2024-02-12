package losowyloch.project.managers;

import losowyloch.project.UiHelper;
import losowyloch.project.entities.Enemy;
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
                "(f) Walcz z " + enemy.getName(),
                "(v) Zobacz statystyki następnego przeciwnika",
                "(g) Zwiększ chwałe o " + this.player.getLvl(),
                "(b) Zobacz swoje statystyki",
                "(q) Wyjdź z programu",
            };
            char input = ui.showAndCollectInput(loopMsg, "fvgbq".toCharArray());
            switch (input) {
                case 'f':
                    System.out.println("Walka z " + enemy.getName() + "\n");
                    FightManager fight = new FightManager(this.player, enemy);
                    fight.fightLoop();
                    // TODO sklep
                    enemy = announceAndGetEnemy();
                    break;
                case 'v':
                    System.out.println("Informacje o następnym przeciwniku\n");
                    this.showInfoAboutEnemy(enemy);
                    break;
                case 'g':
                    this.player.addGlory(this.player.getLvl());
                    System.out.println("Dodano " + this.player.getLvl() + " chwały\nAktualnie posiadasz "
                                       + this.player.getGlory() + " chwały\n");
                    enemy = this.announceAndGetEnemy();
                    break;
                case 'b':
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
