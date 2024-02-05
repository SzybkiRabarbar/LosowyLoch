package losowyloch.project.skills;

import java.util.ArrayList;

public class Skill {
    private String name;
    private int charges;
    private int[] damage;
    private ArrayList<Effect> effects;

    public Skill(String name, int charges, int[] damage, ArrayList<Effect> effects) {
        this.name = name;
        this.charges = charges;
        this.damage = damage;
        this.effects = effects;
    }

    public boolean useCharges() {
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
    public ArrayList<Effect> getEffects() {
        return effects;
    }
}
