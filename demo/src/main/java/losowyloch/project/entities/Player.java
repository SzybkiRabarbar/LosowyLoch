package losowyloch.project.entities;

import java.util.ArrayList;

import losowyloch.project.UiHelper;

public class Player extends Entity {
    private final int id;
    private int exp = 0;
    private int expThreshold = 1;
    private int currency = 0;
    private float glory = 0;
    private ArrayList<Mod> mods = new ArrayList<>();
    private UiHelper ui = new UiHelper();
    private String[] statNames = new String[] {
        "Siła", "Inteligencja", "Zwinność",
        "Witalność", "Obrona", "Wytrzymałość",
        "Szczęście"
    };
    private char[] statLabels = "siavdel".toCharArray();

    public Player(String name, int[] vals, int id) {
        super(name, vals);
        this.id = id;
    }

    public ArrayList<Mod> getMods() {
        return mods;
    }

    public void addMod(Mod mod) {
        mods.add(mod);
        ArrayList<Character> statChars = mod.getStatNames();
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
        ArrayList<Character> statChars = mod.getStatNames();
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
        String[] statsInfo = this.showStats(false);
        System.out.println("Dodaj punkt do statystyk z listy!");
        char picked = ui.showAndCollectInput(statsInfo, statLabels);
        int statNumber = this.getStatMethods().get(picked).getKey().get();
        this.getStatMethods().get(picked).getValue().accept(statNumber + 1);
        System.out.println("Dodano punkt do (" + picked + ")\n");
    }

    public String[] showStats(boolean print) {
        String[] statsInfo = new String[7];
        for (int i = 0; i < statLabels.length; i++) {
            int statNumber = this.getStatMethods().get(statLabels[i]).getKey().get();
            String str = statNames[i] + ": " + statNumber;
            if (print) { System.out.println(str); }
            statsInfo[i] = "(" + statLabels[i] + ") " + str;
        }
        return statsInfo;
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
    public float getGlory() {
        return glory;
    }
}
