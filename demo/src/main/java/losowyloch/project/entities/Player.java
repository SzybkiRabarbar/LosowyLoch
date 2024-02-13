package losowyloch.project.entities;

import java.util.ArrayList;

import losowyloch.project.UiHelper;

public class Player extends Entity {
    private final int id;
    private int exp = 0;
    private int expThreshold = 9;
    private int currency = 9;
    private int glory = 0;
    private int pointPrize = 1;
    private ArrayList<Mod> mods = new ArrayList<>();
    private UiHelper ui = new UiHelper();

    public Player(String name, int[] vals, int id) {
        super(name, vals);
        this.id = id;
    }

    public ArrayList<Mod> getMods() {
        return mods;
    }

    public void addMod(Mod mod) {
        mods.add(mod);
        ArrayList<Character> statChars = mod.getStatAffect();
        ArrayList<Integer> statVals = mod.getStatVals();
        for (int i = 0; i < statChars.size(); i++) {
            Character statChar = statChars.get(i);
            Integer statVal = statVals.get(i);
            int currVal = getStatMethods().get(statChar).getKey().get();
            getStatMethods().get(statChar).getValue().accept(currVal + statVal);
        }
    }

    private void removeMod(Mod mod){
        mods.remove(mod);
        ArrayList<Character> statChars = mod.getStatAffect();
        ArrayList<Integer> statVals = mod.getStatVals();
        for (int i = 0; i < statChars.size(); i++) {
            Character statChar = statChars.get(i);
            Integer statVal = statVals.get(i);
            int currVal = getStatMethods().get(statChar).getKey().get();
            getStatMethods().get(statChar).getValue().accept(currVal - statVal);
        }
    }

    public ArrayList<Mod> useModsDurability() {
        ArrayList<Mod> used = new ArrayList<>();
        for (int i = 0; i < mods.size(); i++) {
            Mod mod = mods.get(i);
            boolean isUsed = mod.useDurability();
            if (isUsed){
                this.removeMod(mod);
                used.add(mod);
            }
        }
        return used;
    }

    public void addCurrency(int gain) {
        this.currency += gain;
    }

    public void addPointPrize() {
        this.pointPrize += 1;
    }

    public boolean subtractCurrency(int number) {
        if (this.currency >= number) {
            this.currency -= number;
            return true;
        } else {
            return false;
        }
    }

    public boolean gainExp(int earnedExp) {
        this.exp += earnedExp;
        if (this.exp >= this.expThreshold) {
            this.exp -= this.expThreshold;
            setLvl(getLvl() + 1);
            this.expThreshold = getLvl() * getLvl();
            return true;
        } else {
            return false; 
        }
    }

    public void addStat() {
        String[] statsInfo = this.getStatsInfo(false);
        System.out.println("Dodaj punkt do statystyk z listy!");
        char picked = ui.showAndCollectInput(statsInfo, getStatLabels());
        int statNumber = this.getStatMethods().get(picked).getKey().get();
        this.getStatMethods().get(picked).getValue().accept(statNumber + 1);
        System.out.println("Dodano punkt do (" + picked + ")\n");
    }

    public void addGlory(int number) {
        this.glory += number;
    }

    public String[] getModsInfo(boolean print) {
        String[] modsInfo = new String[mods.size()];
        for (int i = 0; i < modsInfo.length; i++) {
            modsInfo[i] = mods.get(i).getInfo();
            if (print) {
                System.out.println(modsInfo[i]);
            }
        }
        return modsInfo;
    }
    public void showInfo(){
        System.out.println("Postać:" + getName());
        System.out.println("Poziom " + (this.getLvl() - 2));
        System.out.println("\n* Doświadczenie: " + this.exp + "/" + this.expThreshold);
        System.err.println("* Złoto: " + this.currency);
        System.out.println("* Chwała: " + this.glory);
        System.out.println("\n* Statystyki:\n");
        this.getStatsInfo(true);
        System.out.println("\n* Ulepszenia:\n");
        this.getModsInfo(true);
        System.out.println("\n* Umiejętności (" + getOrginalSkills().size() + "/6):\n");
        this.getSkillsInfo(true, true);
        System.out.println("\n");
    }

    // GETTERS
    public int getId() {
        return id;
    }
    public int getExp() {
        return exp;
    }
    public int getCurrency() {
        return currency;
    }
    public int getGlory() {
        return glory;
    }
    public int getPointPrize() {
        return pointPrize;
    }
}
