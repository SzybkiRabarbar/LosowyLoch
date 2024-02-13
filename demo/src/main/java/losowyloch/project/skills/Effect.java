package losowyloch.project.skills;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Effect {
    private int target;
    private int affects;
    private int power;
    private static String[] labels = {
        "obrażeń", "redukcji obrażeń", "celności", "szansy na unik",
        "siły effektów", "szansy na obrażenia krytyczne",
    };

    public Effect(int target, int affects, int power) {
        this.target = target;
        this.affects = affects;
        this.power = power;
    }

    @JsonCreator
    public Effect() {}

    // METHODS

    @JsonIgnore
    public String getInfo() {
        String targetName = target == 0 ? "gracza" : "przeciwnika";
        String affectsName = labels[this.affects];
        return this.power + "% " + affectsName + " dla " + targetName;
    }

    @JsonIgnore
    public int[] getVals() {
        int[] content = new int[3];
        content[0] = this.target;
        content[1] = this.affects;
        content[2] = this.power;
        return content;
    }

    // SETTERS
    public void setTarget(int target) {
        this.target = target;
    }
    public void setAffects(int affects) {
        this.affects = affects;
    }
    public void setPower(int power) {
        this.power = power;
    }

    // GETTERS
    public int getTarget() {
        return target;
    }
    public int getAffects() {
        return affects;
    }
    public int getPower() {
        return power;
    }
}
