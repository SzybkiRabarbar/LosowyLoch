package losowyloch.project.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.util.Pair;
import losowyloch.project.skills.Skill;

class Entity {
    private String name;
    private int lvl = 1;
    private int strength;
    private int intellect;
    private int agility;
    private int vitality;
    private int defence;
    private int endurance;
    private int luck;
    private ArrayList<Skill> skills = new ArrayList<>();
    private HashMap<Character, Pair<Supplier<Integer>, Consumer<Integer>>> statMethods;

    public Entity(String name, int[] vals) {
        this.name = name;
        this.strength = vals[0];
        this.intellect = vals[1];
        this.agility = vals[2];
        this.vitality = vals[3];
        this.defence = vals[4];
        this.endurance = vals[5];
        this.luck = vals[6];
        statMethods.put('s', new Pair<>(this::getStrength, this::setStrength));
        statMethods.put('i', new Pair<>(this::getIntellect, this::setIntellect));
        statMethods.put('a', new Pair<>(this::getAgility, this::setAgility));
        statMethods.put('v', new Pair<>(this::getVitality, this::setVitality));
        statMethods.put('d', new Pair<>(this::getDefence, this::setDefence));
        statMethods.put('e', new Pair<>(this::getEndurance, this::setEndurance));
        statMethods.put('l', new Pair<>(this::getLuck, this::setLuck));

    }
    
    // SKILLS
    public void addSkill(Skill skill) {
        skills.add(skill);
    }
    public void removeSkill(Skill skill) {
        skills.remove(skill);
    }
    public ArrayList<Skill> checkSkillsCharges() {
        ArrayList<Skill> result = new ArrayList<>();
        for (int i = 0; i < skills.size(); i++) {
            if (skills.get(i).getCharges() <= 0) {
                result.add(skills.remove(i));
            }
        }
        return result;
    }

    // SETTERS
    public void setLvl(int lvl) {
        this.lvl = lvl;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public void setIntellect(int intellect) {
        this.intellect = intellect;
    }
    public void setAgility(int agility) {
        this.agility = agility;
    }
    public void setVitality(int vitality) {
        this.vitality = vitality;
    }
    public void setDefence(int defence) {
        this.defence = defence;
    }
    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }
    public void setLuck(int luck) {
        this.luck = luck;
    }

    // GETTERS
    public HashMap<Character, Pair<Supplier<Integer>, Consumer<Integer>>> getStatMethods() {
        return statMethods;
    }
    public String getName() {
        return name;
    }
    public int getLvl() {
        return lvl;
    }
    public int getStrength() {
        return strength;
    }
    public int getIntellect() {
        return intellect;
    }
    public int getAgility() {
        return agility;
    }
    public int getVitality() {
        return vitality;
    }
    public int getDefence() {
        return defence;
    }
    public int getEndurance() {
        return endurance;
    }
    public int getLuck() {
        return luck;
    }
    public ArrayList<Skill> getSkills() {
        return skills;
    }
}
