package losowyloch.project.entities.creators;

import java.util.Random;

import losowyloch.project.RandomWordPicker;
import losowyloch.project.entities.Enemy;
import losowyloch.project.skills.creators.SkillCreator;

public class EnemyCreator {
    private Random random = new Random();
    public EnemyCreator() {
    }

    private String generateRandomName() {
        String[] randomWords = RandomWordPicker.getRandomWords('r');
        return randomWords[0] + " " + randomWords[1];
    }

    private int[] generateRandomStats(int points) {
        int[] statVals = new int[]{1,1,1,1,1,1,1};
        for (int i = 0; i < points; i++) {
            int index = random.nextInt(7);
            statVals[index]++;
        }
        return statVals;
    }

    private void appendsRandomSkills(Enemy enemy) {
        SkillCreator skillCreator = new SkillCreator(enemy.getLvl(), 1);
        for (int i = 0; i < 4; i++) {
            enemy.addSkill(skillCreator.create());
        } 
    }

    public Enemy create(int lvl, int glory) {
        String name = this.generateRandomName();
        int [] vals = this.generateRandomStats(glory + lvl);
        Enemy enemy = new Enemy(name, vals, glory);
        this.appendsRandomSkills(enemy);
        enemy.setLvl(lvl);
        return enemy;
    }
}
