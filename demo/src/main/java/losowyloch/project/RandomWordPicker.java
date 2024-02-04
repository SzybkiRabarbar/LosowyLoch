package losowyloch.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomWordPicker {
    private static List<String> adjectivesM = new ArrayList<>();
    private static List<String> nounsM = new ArrayList<>();
    private static List<String> adjectivesW = new ArrayList<>();
    private static List<String> nounsW = new ArrayList<>();
    private static Random random = new Random();

    static {
        loadWords("/words/przymiotniki-m.txt", adjectivesM);
        loadWords("/words/rzeczowniki-m.txt", nounsM);
        loadWords("/words/przymiotniki-w.txt", adjectivesW);
        loadWords("/words/rzeczowniki-w.txt", nounsW);
    }

    private static void loadWords(String file, List<String> list) {
        InputStream stream = RandomWordPicker.class.getResourceAsStream(file);
        InputStreamReader czytelnikStrumienia = new InputStreamReader(stream);
        try (BufferedReader reader = new BufferedReader(czytelnikStrumienia)) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String capitalizeEachWord(String original) {
        String[] words = original.split(" ");
        StringBuilder capitalized = new StringBuilder();

        for (String word : words) {
            String firstLetter = word.substring(0, 1).toUpperCase();
            String restOfWord = word.substring(1).toLowerCase();
            capitalized.append(firstLetter).append(restOfWord).append(" ");
        }
        return capitalized.toString().trim();
    }

    public static String[] getRandomWords(char c) {
        List<String> adjectives;
        List<String> nouns;
        switch (c) {
            case 'm':
                adjectives = adjectivesM;
                nouns = nounsM;
                break;
            case 'w':
                adjectives = adjectivesW;
                nouns = nounsW;
                break;
            case 'r':
                boolean randomBool = random.nextBoolean();
                if (randomBool) {
                    adjectives = adjectivesM;
                    nouns = nounsM;
                } else {
                    adjectives = adjectivesW;
                    nouns = nounsW;
                }
                break;
            default:
                adjectives = adjectivesM;
                nouns = nounsM;
                break;
        }
        String adjective = adjectives.get(random.nextInt(adjectives.size()));
        String noun = nouns.get(random.nextInt(nouns.size()));
        return new String[]{capitalizeEachWord(adjective), capitalizeEachWord(noun)};
    }
}