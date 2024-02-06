package losowyloch.project.entities;

import java.util.Random;

public class Enemy extends Entity {
    private int glory;
    private Random random = new Random();

    public Enemy(String name, int[] vals, int glory) {
        super(name, vals);
        this.glory = glory;

    }

    public int returnGlory() {
        return getLvl() / 2;
    }

    public int returnExp() {
        return getLvl() + this.glory;
    }

    public int returnCurrency() {
        return (int) (returnExp() * 2 * (random.nextFloat() + 0.5));
    }
    
    public int getGlory() {
        return glory;
    }
}
