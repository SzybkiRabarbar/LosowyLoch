package losowyloch.project.managers;

import java.util.ArrayList;
import java.util.Random;

import losowyloch.project.entities.Entity;
import losowyloch.project.UiHelper;
import losowyloch.project.entities.Enemy;
import losowyloch.project.entities.Player;
import losowyloch.project.skills.Effect;
import losowyloch.project.skills.Skill;

public class FightManager {
    // Player player;
    // Enemy enemy;
    private UiHelper ui = new UiHelper();
    private Entity[] entities = new Entity[2];
    private Random random = new Random();
    private int[] health = new int[2];
    private float[] damageMult = new float[2];
    private float[] dmgReduction = new float[2];
    private float[] accuracyBuff = new float[2];
    private float[] dodgeChance = new float[2];
    private float[] effectMult= new float[2];
    private float[] critBuff = new float[2];
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
        int lvlCap = (75 + entity.getLvl() * 5);
        this.health[id] = lvlCap - 50 + (entity.getVitality() * 6) + (entity.getEndurance() * 2);
        float dM = (float) (entity.getStrength() * 5 + entity.getIntellect() * 1) / lvlCap;
        this.damageMult[id] = dM > 0.5f ? 0.5f : dM;
        float dR = (float) (entity.getDefence() * 6 + entity.getEndurance() * 3) / lvlCap;
        this.dmgReduction[id] = dR > 0.5f ? 0.5f : dR;
        float aM = (float) (entity.getAgility() * 5 + entity.getIntellect() * 1) / lvlCap;
        this.accuracyBuff[id] = aM > 0.5f ? 0.5f : aM;
        float dC = (float) (entity.getAgility() * 5 + entity.getIntellect() * 1) / lvlCap;
        this.dodgeChance[id] = dC > 0.5f ? 0.5f : dC;
        float eM = (float) (entity.getIntellect() * 5 + entity.getLuck() * 3) / lvlCap;
        this.effectMult[id] = eM > 0.5f ? 0.5f : eM;
        float cC = (float) (entity.getLuck() * 5 + entity.getStrength() * 3) / lvlCap;
        this.critBuff[id] = cC > 0.5f ? 0.5f : cC;
    }

    private void createModedSkills() {
        ArrayList<Skill> rawSkills = entities[this.att].getOrginalSkills();
        ArrayList<Skill> moddedSkills = new ArrayList<>();
        for (Skill rawSkill : rawSkills) {
            String mName = rawSkill.getName();
            int mCharges = rawSkill.getCharges();

            int[] mDamage = rawSkill.getDamage();
            mDamage[0] *= 1 + this.damageMult[this.att] - this.dmgReduction[this.def];
            mDamage[1] *= 1 + this.damageMult[this.att] - this.dmgReduction[this.def];

            float mAccuracy = rawSkill.getAccuracy();
            mAccuracy += this.accuracyBuff[this.att] - this.dodgeChance[this.def];

            float mCritChance = rawSkill.getCritChance();
            mCritChance += this.critBuff[this.att];

            ArrayList<Effect> mEffects = new ArrayList<>();
            for (Effect rawEffect : rawSkill.getEffects()) {
                int mTarget = rawEffect.getTarget();
                int mAffects = rawEffect.getAffects();

                int mPower = rawEffect.getPower();
                mPower *= 1 + this.effectMult[this.att];

                mEffects.add(new Effect(mTarget, mAffects, mPower));
            }
            
            moddedSkills.add(new Skill(mName, mCharges, mDamage, mAccuracy, mCritChance, mEffects));
        }
        entities[att].setModdedSkills(moddedSkills);
    }

    private Skill chooseSkill() {
        int chosenSkill;
        String[] skillsInfo = this.entities[this.att].getSkillsInfo(false, false);
        if (this.att == 0) {
            System.out.println("Twoja tura!\nWybierz umiejętność:\n");
            char[] validChars = new char[skillsInfo.length];
            for (int i = 0; i < skillsInfo.length; i++) {
                skillsInfo[i] = "(" + i + ") " + skillsInfo[i];
                validChars[i] = (char) ('0' + i);
            }
            chosenSkill = (int) this.ui.showAndCollectInput(skillsInfo, validChars) - '0';
        } else {
            System.out.println("Tura przeciwnika!\n");
            chosenSkill = random.nextInt(skillsInfo.length);
        }

        return entities[this.att].getModdedSkillWithId(chosenSkill);
    }

    private int castSkill(Skill skill) {
        System.out.println(this.entities[this.att].getName() + " używa " + skill.getName());
        System.out.println("Szansa na trafienie " + (int) (skill.getAccuracy() * 100) + "%");
        float rand = random.nextFloat();
        System.out.println(rand);
        if (skill.getAccuracy() < rand) {
            System.out.println("Umiejętność nie trafia!\n");
            return 0;
        }
        System.out.print("Umiejętność trafia ");
        int[] dmg = skill.getDamage();
        int damageDealt = random.nextInt(dmg[1] - dmg[0]) + dmg[0];
        if (skill.getCritChance() > random.nextFloat()) {
            damageDealt *= 2;
            System.out.print("KRYTYCZNIE ");
        }
        System.out.println("i zadaje " + damageDealt + " obrażeń!\n");
        // TODO efekty
        return damageDealt;

    }

    private void showBuffs() {
        System.out.println("Modyfikatory: ");
        // TODO pokazanie mnożników
    }

    public void fightLoop() {
        boolean playerAlive = true;
        boolean enemyAlive = true;

        while (playerAlive & enemyAlive) {
            if (this.att == 0) {
            } else {
            }
            this.createModedSkills();
            Skill chosenSkill = this.chooseSkill();
            this.health[this.def] -= castSkill(chosenSkill);
            if (this.health[this.def] <= 0) {
                if (this.def == 1) {
                    enemyAlive = false;
                } else {
                    playerAlive = false;
                }
            }
            int temp = this.att;
            this.att = this.def;
            this.def = temp;
            System.out.println(entities[0].getName() + " | " + health[0] + "  hp");
            System.out.println(entities[1].getName() + " | " + health[1] + "  hp\n");
            System.out.println("Naciśnij dowolny klawisz żeby kontynuować...");  // TODO pokazanie aktualnych modow
            this.ui.getScanner().nextLine();
            this.ui.clearTerminal();
        }
        if (playerAlive) {
            System.out.println(entities[0].getName() + " wygrywa!\n");
            System.out.println("Nagrody!\n");  // TODO
        } else {
            System.out.println(entities[1].getName() + " wygrywa!\n");
            System.out.println("Koniec gry!\n");  // TODO
        }
    }
}
