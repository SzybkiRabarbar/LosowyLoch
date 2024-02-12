package losowyloch.project.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Mod {
    private String name;
    private int durability;
    private ArrayList<Character> statAffect;
    private ArrayList<Integer> statVals;
    private int prize;
    private static HashMap<Character, String> affectNames = new HashMap<Character, String>() {{
        put('s', "siły");
        put('i', "inteligencji");
        put('a', "zwinności");
        put('v', "witalności");
        put('d', "obrony");
        put('e', "wytrzymałości");
        put('l', "szczęścia");
    }};
    public Mod(String name, int durability, Character[] statNames, Integer[] statVals , int prize) {
        this.name = name;
        this.durability = durability;
        this.statAffect = new ArrayList<>(Arrays.asList(statNames));
        this.statVals = new ArrayList<>(Arrays.asList(statVals));
        this.prize = prize;
    }

    public boolean useDurability() {
        this.durability--;
        return this.durability == 0;
    }

    public String getInfo() {
        String modInfo = this.name + " | Pozostałe użycia: " + this.durability;
        for (int i = 0; i < statVals.size(); i++) {
            modInfo += "\n  +" + this.statVals.get(i) + " do " + affectNames.get(this.statAffect.get(i));
        }
        return modInfo;
    }

    // GETTERS
    public String getName() {
        return name;
    }
    public int getDurability() {
        return durability;
    }
    public ArrayList<Character> getStatAffect() {
        return statAffect;
    }
    public ArrayList<Integer> getStatVals() {
        return statVals;
    }
    public int getPrize() {
        return prize;
    }
}
