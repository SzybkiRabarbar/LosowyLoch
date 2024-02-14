package losowyloch.project;

import losowyloch.project.entities.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerJsonUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String JSON_FILE_PATH;

    static {
        String tempPath = null;
        URL res = PlayerJsonUtil.class.getClassLoader().getResource("data.json");
        if (res == null) {
            throw new IllegalStateException("Cannot find file: data.json");
        } else {
            try {
                tempPath = Paths.get(res.toURI()).toFile().getAbsolutePath();
            } catch (URISyntaxException e) {
                // Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (tempPath == null) {
                    throw new IllegalStateException("JSON_FILE_PATH has not been initialized");
                } else {
                    JSON_FILE_PATH = tempPath;
                }
            }
        }
    }

    public static List<Player> fetchPlayersFromJson() {
        List<Player> players = new ArrayList<>();

        try {
            players = OBJECT_MAPPER.readValue(new File(JSON_FILE_PATH), new TypeReference<List<Player>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return players;
    }

    public static void replacePlayerInJsonFile(Player newPlayer) {
        try {
            List<Player> players = OBJECT_MAPPER.readValue(new File(JSON_FILE_PATH), new TypeReference<List<Player>>(){});
            players = players.stream()
                    .map(player -> player.getID() == newPlayer.getID() ? newPlayer : player)
                    .collect(Collectors.toList());
            OBJECT_MAPPER.writeValue(new File(JSON_FILE_PATH), players);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addPlayerToJsonFile(Player newPlayer) {
        try {
            List<Player> players = OBJECT_MAPPER.readValue(new File(JSON_FILE_PATH), new TypeReference<List<Player>>(){});
            players.add(newPlayer);
            OBJECT_MAPPER.writeValue(new File(JSON_FILE_PATH), players);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removePlayerFromJsonFile(int playerId) {
        try {
            List<Player> players = OBJECT_MAPPER.readValue(new File(JSON_FILE_PATH), new TypeReference<List<Player>>(){});
            players = players.stream()
                    .filter(player -> player.getID() != playerId)
                    .collect(Collectors.toList());
            OBJECT_MAPPER.writeValue(new File(JSON_FILE_PATH), players);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
