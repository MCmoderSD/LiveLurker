package de.MCmoderSD.main;

import de.MCmoderSD.UI.Frame;
import de.MCmoderSD.core.BotClient;
import de.MCmoderSD.utilities.database.MySQL;
import de.MCmoderSD.utilities.json.JsonNode;
import de.MCmoderSD.utilities.json.JsonUtility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class Main {

    // Constants
    private static final String CONFIG = "/config/Config.json";
    private static final String CHANNEL_LIST = "/config/Channel.list";
    private static final String MYSQL_CONFIG = "/config/database.json";

    // Associations
    private final BotClient botClient;

    // Constructor
    public Main(ArrayList<String> args) {
        JsonUtility jsonUtility = new JsonUtility();

        // CLI check
        Frame frame = null;
        if (!(args.contains("-cli") || args.contains("-nogui"))) frame = new Frame(this);

        // Load Bot Config
        JsonNode config = jsonUtility.load(CONFIG);

        String username = config.get("username").asText();   // Get Username
        String token = config.get("token").asText();         // Get Token
        String prefix = config.get("prefix").asText();       // Get Prefix

        // Load Channel List
        String[] channels = null;
        try {
            ArrayList<String> lines = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();
            InputStream inputStream = getClass().getResourceAsStream(CHANNEL_LIST);
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8));

            String line;
            while ((line = reader.readLine()) != null) lines.add(line);
            for (String name : lines) if (name.length() > 3) names.add(name.replace("\n", "").replace(" ", ""));
            channels = new String[names.size()];
            names.toArray(channels);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        if (channels == null) throw new IllegalArgumentException("Channel List is empty!");

        // Load MySQL Config
        MySQL mySQL = args.contains("-log") ? new MySQL(jsonUtility.load(MYSQL_CONFIG), frame) : null;

        // Init Bot
        botClient = new BotClient(username, token, prefix, channels, mySQL);
    }

    // PSVM
    public static void main(String[] args) {
        ArrayList<String> arguments = new ArrayList<>();
        for (String arg : args) if (arg.startsWith("-")) arguments.add(arg);
        new Main(arguments);
    }

    // Getter
    public BotClient getBotClient() {
        return botClient;
    }
}