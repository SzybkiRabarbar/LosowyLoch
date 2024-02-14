package losowyloch.project.entities.creators;

import losowyloch.project.RandomWordPicker;
import losowyloch.project.entities.Mod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ModCreator {
    private Random random = new Random();
    private int lvl;
    private static String[] modsNames = new String[] {
        "Zbroja",
        "Hełm",
        "Łuk",
        "Kusza",
        "Pancerz",
        "Tłumik",
        "Granat",
        "Kij",
        "Młot",
        "Sztylet",
        "Laska",
        "Kopień",
        "Pistolet",
        "Karabin",
        "Rewolwer",
        "Muszkiet",
        "Katana",
        "Szabla",
        "Rapier",
        "Miecz",
        "Tarcza",
        "Grot",
        "Klinga",
        "Puklerz",
        "Kolczuga",
        "Halabarda",
        "Kordelas",
        "Scimitar",
        "Kijek",
        "Korbik",
        "Kabura",
        "Kołczan",
        "Strzała",
    };
    private static boolean[] modsGenderForm = new boolean[] {
        false, // Zbroja
        true, // Hełm
        true, // Łuk
        false, // Kusza
        true, // Pancerz
        true, // Tłumik
        true, // Granat
        true, // Kij
        true, // Młot
        true, // Sztylet
        false, // Laska
        true, // Kopień
        true, // Pistolet
        true, // Karabin
        true, // Rewolwer
        true, // Muszkiet
        false, // Katana
        false, // Szabla
        true, // Rapier
        true, // Miecz
        false, // Tarcza
        true, // Grot
        false, // Klinga
        true, // Puklerz
        false, // Kolczuga
        false, // Halabarda
        true, // Kordelas
        true, // Scimitar
        true, // Kijek
        true, // Korbik
        false, // Kabura
        true, // Kołczan
        false, // Strzała
    };
    private List<Character> affectNames = new ArrayList<>();

    public ModCreator(int lvl) {
        this.lvl = lvl;
        for (Character character : "siavdel".toCharArray()) {
            this.affectNames.add(character);
        }
    }
    
    public Mod create() {
        int nameId = random.nextInt(modsNames.length);
        String adjective = RandomWordPicker.getRandomWords(modsGenderForm[nameId] ? 'm' : 'w')[0];
        String name = adjective + " " + modsNames[nameId];
        int points = (int) ((random.nextFloat() + 0.5f) * this.lvl) + 2;
        int prize = (int) ((random.nextFloat() + 0.5f) * points) + 2;
        int durability = random.nextInt(4) + 2;
        Collections.shuffle(this.affectNames);
        Character[] affect = new Character[random.nextInt(3) + 1];
        for (int i = 0; i < affect.length; i++) {
            affect[i] = this.affectNames.get(i);
        }
        Integer[] vals = new Integer[affect.length];
        for (int i = 0; i < points; i++) {
            int id = random.nextInt(vals.length);
            if (vals[id] == null) { 
                vals[id] = 1;
            } else {
                vals[id] += 1;
            }
        }
        for (int i = 0; i < vals.length; i++) {
            if (vals[i] == null) {
                vals[i] = 1;
            }
        }
        return new Mod(name, durability, affect, vals, prize);
    }
}
