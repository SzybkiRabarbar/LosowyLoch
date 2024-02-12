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
    private static String[] skillsModsNames = new String[] {
        "         Zwiększenie zadawanych obrażeń: ",
        "     Zmniejszenie otrzymywanych obrażeń: ",
        "          Dodatkowa szansa na trafienie: ",
        "                         Szansa na unik: ",
        "   Zwięszenie siły efektów umiejętności: ",
        "Zwięszenie szans na trafienie krytyczne: ",
    };
    private UiHelper ui = new UiHelper();
    private Player player;
    private Entity[] entities = new Entity[2];  // 0 - Player, 1 - Enemy
    private Random random = new Random();
    private int[] health = new int[2];
    private float[] damageMult = new float[2];
    private float[] dmgReduction = new float[2];
    private float[] accuracyBuff = new float[2];
    private float[] dodgeChance = new float[2];
    private float[] effectMult= new float[2];
    private float[] critBuff = new float[2];
    private float[][] skillsModifiers = {damageMult, dmgReduction, accuracyBuff, dodgeChance, effectMult, critBuff};
    private int att = 0;  // attacker
    private int def = 1;  // defender

    public FightManager(Player player, Enemy enemy) {
        this.player = player;
        entities[0] = player;
        entities[1] = enemy;
        this.calculateSkillsModifiers(entities[0], 0);
        this.calculateSkillsModifiers(entities[1], 1);
    }

    private void calculateSkillsModifiers(Entity entity, int id) {
        int lvlCap = (75 + entity.getLvl() * 5);
        this.health[id] = lvlCap - 60 + (entity.getVitality() * 6) + (entity.getEndurance() * 3);

        float dM = (float) (entity.getStrength() * 5 + entity.getIntellect() * 1) / lvlCap;
        this.damageMult[id] = dM > 0.5f ? 0.5f : dM;

        float dR = (float) (entity.getDefence() * 6 + entity.getEndurance() * 2) / lvlCap;
        this.dmgReduction[id] = dR > 0.5f ? 0.5f : dR;

        float aM = (float) (entity.getAgility() * 5 + entity.getIntellect() * 1) / lvlCap;
        this.accuracyBuff[id] = aM > 0.5f ? 0.5f : aM;

        float dC = (float) (entity.getAgility() * 3 + entity.getIntellect() * 1) / lvlCap;
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

            int[] mDamage = new int[]{rawSkill.getDamage()[0], rawSkill.getDamage()[1]};
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

    private void useSkillCharge(Skill moddedSkill) {
        int index = entities[this.att].getModdedSkills().indexOf(moddedSkill);
        Skill orginalSkill = entities[this.att].getOrginalSkills().get(index);
        if (orginalSkill.useChargeAndCheckIfUtilized()) {
            this.entities[this.att].removeSkill(orginalSkill);
        }
    }

    private int castSkill(Skill skill) {
        System.out.println(this.entities[this.att].getName() + " używa " + skill.getName());
        this.useSkillCharge(skill);

        System.out.println("Szansa na trafienie " + (int) (skill.getAccuracy() * 100) + "%");
        if (skill.getAccuracy() < random.nextFloat()) {
            System.out.println("Umiejętność nie trafia!\n");
            return 0;
        }
        System.out.print("Umiejętność trafia ");

        int[] dmg = skill.getDamage();
        if (dmg[0] == dmg[1]) {dmg[1] += 1;}
        int damageDealt = random.nextInt(dmg[1] - dmg[0]) + dmg[0];

        if (skill.getCritChance() > random.nextFloat()) {
            damageDealt *= 2;
            System.out.print("KRYTYCZNIE ");
        }

        System.out.println("i zadaje " + damageDealt + " obrażeń!\n");

        for (Effect eff : skill.getEffects()) {
            float modifier = (float) (100 + eff.getPower()) / 100;
            System.out.println(eff.getInfo());
            this.skillsModifiers[eff.getAffects()][eff.getTarget()] *= modifier;
        }

        return damageDealt;

    }

    private void showBuffs() {
        System.out.println("Modyfikatory: ");
        System.out.println(entities[0].getName() + " | " + entities[1].getName());
        for (int i = 0; i < 6; i++) {
            String playerMod = String.format("%-" + 3 + "s", ((int) (this.skillsModifiers[i][0] * 100)) + "%");
            String enemyMod = String.format("%-" + 3 + "s", ((int) (this.skillsModifiers[i][1] * 100)) + "%");
            System.out.println(skillsModsNames[i] + playerMod + " | " + enemyMod);
        }
    }

    private void endRoundCredits() {
        System.out.println(entities[0].getName() + " | " + health[0] + "  hp");
        System.out.println(entities[1].getName() + " | " + health[1] + "  hp\n");
        System.out.println("Naciśnij:");
        System.out.println("    (a) aby zobaczyć informacje o modyfikatorach");
        System.out.println("    (s) aby zobaczyć informacje o umiejętnościach przeciwnika");
        System.out.println("    (d) aby zobaczyć informacje o umiejętnościach gracza");
        System.out.println("    ( ) dowolny przycisk aby kontynuować...\n");
        String input = this.ui.getScanner().nextLine();
        System.err.println();
        if (input.length() > 0) {
            switch (input.charAt(0)) {
                case 'a':
                    this.showBuffs();
                    break;
                case 's':
                    System.out.println("Orginalne:\n");
                    this.entities[1].getSkillsInfo(true, true);
                    System.out.println("Z modyfikatorami:\n");
                    this.entities[1].getSkillsInfo(false, true);
                    break;
                case 'd':
                    System.out.println("Orginalne:\n");
                    this.entities[0].getSkillsInfo(true, true);
                    break;
                default:
                    break;
            }
        }
    }

    private void playerPrize() {
        int lvl = this.player.getLvl();

        int currency = this.player.getGlory() + lvl + random.nextInt(lvl);
        this.player.addCurrency(currency);

        int exp = lvl;
        for (int i = 0; i < this.player.getGlory(); i++) {
            if (random.nextBoolean()) { exp += 1; }
        }
        boolean lvlUp = this.player.gainExp(exp);

        int glory = random.nextInt(lvl) + lvl;
        this.player.addGlory(glory);

        System.out.println("Otrzymano:");
        System.out.println("    " + currency + " złota");
        System.out.println("    " + exp + " doświadczenia");
        System.out.println("    " + glory + " chwały\n");

        if (lvlUp) {
            this.player.addGlory(this.player.getLvl());
            this.player.addCurrency(this.player.getGlory());
            System.out.println("Udało ci się zdobyć " + (this.player.getLvl() - 2) + " poziom!");
            System.out.println("Otrzymujesz dodatkowe " + this.player.getGlory() + " złota! \n");
            System.out.println("Otrzymujesz punkt umiejętności!");
            player.addStat();
        }
    }

    public void fightLoop() {
        boolean playerAlive = true;
        boolean enemyAlive = true;
        System.out.println(entities[0].getName() + " | " + health[0] + "  hp");
        System.out.println(entities[1].getName() + " | " + health[1] + "  hp\n");
        this.showBuffs();
        System.out.println();

        while (playerAlive & enemyAlive) {
            this.createModedSkills();
            Skill chosenSkill = this.chooseSkill();
            this.health[this.def] -= castSkill(chosenSkill);
            this.endRoundCredits();
            this.ui.clearTerminal();

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
        }
        if (playerAlive) {
            System.out.println(entities[0].getName() + " wygrywa!\n");
            this.playerPrize();
        } else {
            System.out.println(entities[1].getName() + " wygrywa!\n");
            System.out.println("Koniec gry!\n");  // TODO
            System.exit(0);
        }
        System.out.println("Naciśnij dowolny przycisk aby kontynuować...\n");
        this.ui.getScanner().nextLine();
}
}
