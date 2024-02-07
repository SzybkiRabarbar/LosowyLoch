package losowyloch.project.managers;

import java.util.ArrayList;
import java.util.Random;

import losowyloch.project.entities.Entity;
import losowyloch.project.entities.Enemy;
import losowyloch.project.entities.Player;
import losowyloch.project.skills.Effect;
import losowyloch.project.skills.Skill;

public class FightManager {
    // Player player;
    // Enemy enemy;
    private Entity[] entities = new Entity[2];
    private Random random = new Random();
    private int[] health = new int[2];
    private float[] damageMult = new float[2];
    private float[] dmgReduction = new float[2];
    private float[] accuracyMult = new float[2];
    private float[] dodgeChance = new float[2];
    private float[] effectMult= new float[2];
    private float[] critMult = new float[2];
    private int att = 0;  // attacker
    private int def = 1;  // defender

    public FightManager(Player player, Enemy enemy) {
        // this.player = player;
        // this.enemy = enemy;
        entities[0] = player;
        entities[1] = enemy;
        this.calculateMults(entities[0], 0);
        this.calculateMults(entities[1], 1);
    }

    private void calculateMults(Entity entity, int id) {
        int lvlCap = (100 + entity.getLvl() * 5);
        this.health[id] = lvlCap - 50 + (entity.getVitality() * 6) + (entity.getEndurance() * 2);
        float dM = (entity.getStrength() * 5 + entity.getIntellect() * 1) / lvlCap;
        this.damageMult[id] = dM > 0.5f ? 0.5f : dM;
        float dR = (entity.getDefence() * 6 + entity.getEndurance() * 3) / lvlCap;
        this.dmgReduction[id] = dR > 0.5f ? 0.5f : dR;
        float aM = (entity.getAgility() * 5 + entity.getIntellect() * 1) / lvlCap;
        this.accuracyMult[id] = aM > 0.5f ? 0.5f : aM;
        float dC = (entity.getAgility() * 5 + entity.getIntellect() * 1) / lvlCap;
        this.dodgeChance[id] = dC > 0.5f ? 0.5f : dC;
        float eM = (entity.getIntellect() * 5 + entity.getLuck() * 3) / lvlCap;
        this.effectMult[id] = eM > 0.5f ? 0.5f : eM;
        float cC = (entity.getLuck() * 5 + entity.getStrength() * 3) / lvlCap;
        this.critMult[id] = cC > 0.5f ? 0.5f : cC;
    }

    private void createModedSkills() {
        ArrayList<Skill> rawSkills = entities[this.att].getOrginalSkills();
        ArrayList<Skill> moddedSkills = new ArrayList<>();
        for (Skill rawSkill : rawSkills) {
            String mName = rawSkill.getName();
            int mCharges = rawSkill.getCharges();

            int[] mDamage = rawSkill.getDamage();
            mDamage[0] *= this.accuracyMult[att] - this.dmgReduction[def];
            mDamage[1] *= this.accuracyMult[att] - this.dmgReduction[def];

            float mAccuracy = rawSkill.getAccuracy();
            mAccuracy += this.accuracyMult[att] - this.dodgeChance[def];

            float mCritChance = rawSkill.getCritChance();
            mCritChance += this.critMult[att];

            ArrayList<Effect> mEffects = new ArrayList<>();
            for (Effect rawEffect : rawSkill.getEffects()) {
                int mTarget = rawEffect.getTarget();
                int mAffects = rawEffect.getAffects();

                int mPower = rawEffect.getPower();
                mPower *= this.effectMult[att];

                mEffects.add(new Effect(mTarget, mAffects, mPower));
            }
            
            moddedSkills.add(new Skill(mName, mCharges, mDamage, mAccuracy, mCritChance, mEffects));
        }
        entities[att].setModdedSkills(moddedSkills);
    }

    public void fightLoop() {
        boolean playerAlive = true;
        boolean enemyAlive = true;
        while (playerAlive & enemyAlive) {
            this.createModedSkills();
        }
    }
}
