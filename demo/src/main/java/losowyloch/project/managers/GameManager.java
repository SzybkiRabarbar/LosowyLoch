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
        System.out.println("\nStatystyki:");
        enemy.getStatsInfo(true);
        System.out.println("\nUmiejętności:");
        enemy.getSkillsInfo(true, true);
        System.out.println("\n");
    }

    private Enemy announceAndGetEnemy(){
        Enemy enemy = enemyCreator.create(player.getLvl(), player.getGlory()); 
        System.out.println("Następny przeciwnik: " + enemy.getName() + "\n");
        return enemy;
    }

    public void mainLoop() {
        System.out.println("Rozpoczęcie rozgrywki jako " + player.getName() + "\n");
        Enemy enemy = this.announceAndGetEnemy();
        boolean isRunnig = true;
        while (isRunnig) {
            String[] loopMsg = new String[] {
                "Wybierz co chcesz zrobić:",
                "(f) Walcz z " + enemy.getName(),
                "(v) Zobacz statystyki następnego przeciwnika",
                "(g) Zwiększ chwałe o " + player.getLvl(),
                "(b) Zobacz swoje statystyki",
                "(q) Wyjdź z programu",
            };
            char input = ui.showAndCollectInput(loopMsg, "fvgbq".toCharArray());
            switch (input) {
                case 'f':
                    System.out.println("Walka z " + enemy.getName() + "\n");
                    FightManager fight = new FightManager(player, enemy);
                    fight.fightLoop();
                    enemy = announceAndGetEnemy();  // Nowy przecinik po wygranej walce
                    break;
                case 'v':
                    System.out.println("Informacje o następnym przeciwniku\n");
                    this.showInfoAboutEnemy(enemy);
                    break;
                case 'g':
                    player.addGlory(player.getLvl());
                    System.out.println("Dodano " + player.getLvl() + " chwały\nAktualnie posiadasz "
                                       + player.getGlory() + " chwały\n");
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
