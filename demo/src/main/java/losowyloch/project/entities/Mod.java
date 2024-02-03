package losowyloch.project.entities;

import java.util.ArrayList;
import java.util.Arrays;

class Mod {
    private String name;
    private int durability;
    private ArrayList<Character> statNames;
    private ArrayList<Integer> statVals;

    public Mod(String name, int durability, Character[] statNames, Integer[] statVals) {
        this.name = name;
        this.durability = durability;
        this.statNames = new ArrayList<>(Arrays.asList(statNames));
        this.statVals = new ArrayList<>(Arrays.asList(statVals));
    }

    public boolean useDurability() {
        this.durability--;
        return this.durability == 0;
    }

    // GETTERS
    public String getName() {
        return name;
    }
    public int getDurability() {
        return durability;
    }
    public ArrayList<Character> getStatNames() {
        return statNames;
    }
    public ArrayList<Integer> getStatVals() {
        return statVals;
    }
}
