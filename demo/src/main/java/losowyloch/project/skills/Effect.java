package losowyloch.project.skills;

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

    public String getInfo() {
        String targetName = target == 0 ? "gracza" : "przeciwnika";
        String affectsName = labels[this.affects];
        return this.power + "% " + affectsName + " dla " + targetName;
    }

    public int[] getVals() {
        int[] content = new int[3];
        content[0] = this.target;
        content[1] = this.affects;
        content[2] = this.power;
        return content;
    }
}
