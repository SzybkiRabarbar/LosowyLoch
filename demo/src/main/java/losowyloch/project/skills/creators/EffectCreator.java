package losowyloch.project.skills.creators;

import java.util.Random;

import losowyloch.project.skills.Effect;

class EffectCreator {
    private Random random = new Random();
    private int lvl;
    private int master;
    public EffectCreator(int lvl, int master) {
        this.lvl = lvl;
        this.master = master;
    }

    public Effect create() {
        int target;
        int mult;
        boolean isGood = random.nextBoolean();
        if (isGood) {
            target = this.master;
            mult = 1;
        } else {
            target = this.master == 0 ? 1 : 0;
            mult = -1;
        }
        int affects = random.nextInt(6);
        int bound = this.lvl > 20 ? 20 : this.lvl; 
        int power = (random.nextInt(bound) + 1) * mult;
        return new Effect(target, affects, power);
    }
}
