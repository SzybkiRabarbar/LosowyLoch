package losowyloch.project.skills;

class Effect {
    private int target;
    private int effect;
    private float power;

    public Effect(int target, int effect, float power) {
        this.target = target;
        this.effect = effect;
        this.power = power;
    }

    public Object[] getVals() {
        Object[] content = new Object[3];
        content[0] = this.target;
        content[1] = this.effect;
        content[2] = this.power;
        return content;
    }
}
