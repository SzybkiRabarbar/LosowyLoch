package losowyloch.project.skills.creators;

import java.util.ArrayList;
import java.util.Random;

import losowyloch.project.skills.Effect;
import losowyloch.project.skills.Skill;
import losowyloch.project.RandomWordPicker;

public class SkillCreator {
    private static String[] attackNouns = new String[]{
        "Atak", "Strzał", "Cios",
        "Zadzior", "Wybuch", "Rozbłysk",
        "Piorun", "Fala", "Zamach",
        "Rzut", "Szpikulec", "Krzyk",
        "Podmuch", "Zasłona", "Błyskawica",
        "Wir", "Eksplozja", "Sztorm",
        "Meteoryt", "Lawina",
    };
    private static boolean[] attackGender = new boolean[]{
        true, // Atak
        true, // Strzał
        true, // Cios
        true, // Zadzior
        true, // Wybuch
        true, // Rozbłysk
        true, // Piorun
        false, // Fala
        true, // Zamach
        true, // Rzut
        true, // Szpikulec
        true, // Krzyk
        true, // Podmuch
        false, // Zasłona
        false, // Błyskawica
        true, // Wir
        false, // Eksplozja
        true, // Sztorm
        true, // Meteoryt
        false, // Lawina
    };
    private EffectCreator effectCreator;
    private int lvl;
    Random random = new Random();

    public SkillCreator(int lvl, int master) {
        this.lvl = lvl;
        this.effectCreator = new EffectCreator(this.lvl, master);
    }

    private String createName() {
        int randIndex = random.nextInt(attackNouns.length);
        char gender = attackGender[randIndex] ? 'm' : 'w';
        String adjective = RandomWordPicker.getRandomWords(gender)[0];
        return adjective + " " + attackNouns[randIndex];
    }

    private int[] createDamage() {
        int lowMin = (int) (this.lvl * 0.5);
        int lowMax = (int) (this.lvl * 1.5);
        int highMin = (int) (this.lvl * 1.5) + 1;
        int highMax = (int) (this.lvl * 2.5) + 1;
        int low = random.nextInt((lowMax - lowMin) + 1) + lowMin;
        int high = random.nextInt((highMax - highMin) + 1) + highMin;
        return new int[] {low, high};
    }

    private ArrayList<Effect> createEffects() {
        int effectsNumber = 1 + (int) (this.lvl / 10);
        if (random.nextInt(11) <= this.lvl % 10) { effectsNumber++; }
        ArrayList<Effect> effects = new ArrayList<>();
        for (int i = 0; i < effectsNumber; i++) {
            effects.add(effectCreator.create());
        }
        return effects;
    }

    public Skill create() {
        String name = createName();
        int[] damage = createDamage();
        int charges = random.nextInt(11 + this.lvl) + 5 + this.lvl;
        float minAc = 0.35f;
        float maxAc = 0.7f;
        float accuracy = minAc + random.nextFloat() * (maxAc - minAc);
        float critChance = random.nextFloat() * 0.5f;
        ArrayList<Effect> effects = createEffects();
        return new Skill(name, charges, damage, accuracy, critChance, effects);
    }
}
