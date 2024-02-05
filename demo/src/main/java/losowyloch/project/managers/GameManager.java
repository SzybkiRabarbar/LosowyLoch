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

    public void mainLoop() {
        // Enemy test
        Enemy enemy = enemyCreator.create(player.getLvl(), player.getGlory()); 
        System.err.println(enemy.getName());
        enemy.getStatsInfo(true);
        enemy.getSkillsInfo(true);
        // 
        while (true) {
            break;  // TODO mainloop programu decydujący co dalej zrobić
        }
    }
}
