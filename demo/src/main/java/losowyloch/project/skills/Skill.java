package losowyloch.project.skills;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Skill {
    private String name;
    private int charges;
    private int[] damage;
    private float accuracy;
    private float critChance;
    private ArrayList<Effect> effects;

    public Skill(String name, int charges, int[] damage, float accuracy, float critChance, ArrayList<Effect> effects) {
        this.name = name;
        this.charges = charges;
        this.damage = damage;
        this.accuracy = accuracy;
        this.critChance = critChance;
        this.effects = effects;
    }

    @JsonCreator
    public Skill() {}

    // METHODS

    public boolean useChargeAndCheckIfUtilized() {
        this.charges--;
        return this.charges <= 0;
    }

    @JsonIgnore
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

    // SETTERS
    public void setName(String name) {
        this.name = name;
    }
    public void setCharges(int charges) {
        this.charges = charges;
    }
    public void setDamage(int[] damage) {
        this.damage = damage;
    }
    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }
    public void setCritChance(float critChance) {
        this.critChance = critChance;
    }
    public void setEffects(ArrayList<Effect> effects) {
        this.effects = effects;
    }

    // GETTERS
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
