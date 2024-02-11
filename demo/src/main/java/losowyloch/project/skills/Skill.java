package losowyloch.project.skills;

import java.util.ArrayList;

public class Skill {
    private String name;
    private int charges;
    private int[] damage;
    private ArrayList<Effect> effects;
    private float accuracy;
    private float critChance;

    public Skill(String name, int charges, int[] damage, float accuracy, float critChance, ArrayList<Effect> effects) {
        this.name = name;
        this.charges = charges;
        this.damage = damage;
        this.accuracy = accuracy;
        this.critChance = critChance;
        this.effects = effects;
    }

    public boolean useChargeAndCheckIfUtilized() {
        this.charges--;
        return this.charges == 0;
    }

    // GETTERS
    public String getInfo() {
        int[] damage = this.getDamage();
        String effectsInfo = "";
        for (Effect eff : effects) {
            effectsInfo += "\n  " + eff.getInfo();
        }    
        return this.name + "\n" +
               "Ilość użyć: " + this.charges + "\n" +
               "Obrażenia: " + damage[0] + "-" + damage[1] + "\n" +
               "Celność: " + (int) (this.accuracy * 100) + "%\n" +
               "Szansa na trafienie krytyczne: " + (int) (this.critChance * 100) + "%\n" +
               "Efekty:" + effectsInfo;
    }
    public String getName() {
        return name;
    }
    public int getCharges() {
        return charges;
    }
    public int[] getDamage() {
        return damage;
    } 
    public float getAccuracy() {
        return accuracy;
    }
    public float getCritChance() {
        return critChance;
    }
    public ArrayList<Effect> getEffects() {
        return effects;
    }
}
