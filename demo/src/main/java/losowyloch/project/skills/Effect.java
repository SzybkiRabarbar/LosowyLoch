package losowyloch.project.skills;

public class Effect {
    private int target;
    private int affects;
    private int power;

    public Effect(int target, int affects, int power) {
        this.target = target;
        this.affects = affects;
        this.power = power;
    }

    public int[] getVals() {
        int[] content = new int[3];
        content[0] = this.target;
        content[1] = this.affects;
        content[2] = this.power;
        return content;
    }
}
