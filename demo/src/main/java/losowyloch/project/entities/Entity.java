package losowyloch.project.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.util.Pair;
import losowyloch.project.skills.Skill;

public class Entity {
    private String name;
    private int lvl = 3;
    private int strength;
    private int intellect;
    private int agility;
    private int vitality;
    private int defence;
    private int endurance;
    private int luck;
    private ArrayList<Skill> orginalSkills = new ArrayList<>();
    private ArrayList<Skill> moddedSkills = new ArrayList<>();
    private HashMap<Character, Pair<Supplier<Integer>, Consumer<Integer>>> statMethods;
    private static String[] statNames = new String[] {
        "Siła", "Inteligencja", "Zwinność",
        "Witalność", "Obrona", "Wytrzymałość",
        "Szczęście"
    };
    private static char[] statLabels = "siavdel".toCharArray();

    public Entity(String name, int[] vals) {
        this.name = name;
        this.strength = vals[0];
        this.intellect = vals[1];
        this.agility = vals[2];
        this.vitality = vals[3];
        this.defence = vals[4];
        this.endurance = vals[5];
        this.luck = vals[6];
        this.statMethods = new HashMap<>();
        this.statMethods.put('s', new Pair<>(this::getStrength, this::setStrength));
        this.statMethods.put('i', new Pair<>(this::getIntellect, this::setIntellect));
        this.statMethods.put('a', new Pair<>(this::getAgility, this::setAgility));
        this.statMethods.put('v', new Pair<>(this::getVitality, this::setVitality));
        this.statMethods.put('d', new Pair<>(this::getDefence, this::setDefence));
        this.statMethods.put('e', new Pair<>(this::getEndurance, this::setEndurance));
        this.statMethods.put('l', new Pair<>(this::getLuck, this::setLuck));

    }
    
    // SKILLS
    public void addSkill(Skill skill) {
        orginalSkills.add(skill);
    }
    public void removeSkill(Skill skill) {
        orginalSkills.remove(skill);
    }
    public ArrayList<Skill> checkSkillsCharges() {
        ArrayList<Skill> result = new ArrayList<>();
        for (int i = 0; i < orginalSkills.size(); i++) {
            if (orginalSkills.get(i).getCharges() <= 0) {
                result.add(orginalSkills.remove(i));
            }
        }
        return result;
    }

    public String[] getStatsInfo(boolean print) {
        String[] statsInfo = new String[7];
        for (int i = 0; i < statLabels.length; i++) {
            int statNumber = this.getStatMethods().get(statLabels[i]).getKey().get();
            String str = statNames[i] + ": " + statNumber;
            if (print) { System.out.println(str); }
            statsInfo[i] = "(" + statLabels[i] + ") " + str;
        }
        return statsInfo;
    }

    public String[] getSkillsInfo(boolean orginal, boolean print) {
        ArrayList<Skill> skills = orginal ? this.orginalSkills : this.moddedSkills;
        int ln = skills.size();
        String[] skillsInfo = new String[ln];
        for (int i = 0; i < ln; i++) {
            Skill currSkill = skills.get(i);
            skillsInfo[i] = "\n" + currSkill.getInfo();
            if (print) {
                System.out.println(skillsInfo[i]);
            }
        }
        return skillsInfo;
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
    public void setModdedSkills(ArrayList<Skill> moddedSkills) {
        this.moddedSkills = moddedSkills;
    }

    // GETTERS
    public char[] getStatLabels() {
        return statLabels;
    }
    public String[] getStatNames() {
        return statNames;
    }
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
    public ArrayList<Skill> getOrginalSkills() {
        return orginalSkills;
    }
    public ArrayList<Skill> getModdedSkills() {
        return moddedSkills;
    }
}
