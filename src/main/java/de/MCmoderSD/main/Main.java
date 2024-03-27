package de.MCmoderSD.main;

import de.MCmoderSD.UI.Frame;
import de.MCmoderSD.core.BotClient;

import de.MCmoderSD.utilities.database.MySQL;
import de.MCmoderSD.utilities.json.JsonNode;
import de.MCmoderSD.utilities.json.JsonUtility;
import de.MCmoderSD.utilities.other.Reader;

import java.util.ArrayList;

public class Main {

    // Constants
    private static final String BOT_CONFIG = "/config/Config.json";
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

        // Logging check
        MySQL mySQL = new MySQL(jsonUtility.load(MYSQL_CONFIG), frame, !args.contains("-log"));

        // Load Bot Config
        JsonNode botConfig = jsonUtility.load(BOT_CONFIG);

        String botName = botConfig.get("username").asText().toLowerCase();     // Get Bot Name
        String botToken = botConfig.get("token").asText();   // Get Bot Token
        String prefix = botConfig.get("prefix").asText();       // Get Prefix

        // Load Channel List
        Reader reader = new Reader();
        ArrayList<String> channels = new ArrayList<>();
        for (String channel : reader.lineRead(CHANNEL_LIST)) if (channel.length() > 3) channels.add(channel.replace("\n", "").replace(" ", ""));
        if (!args.contains("-dev")) {
            ArrayList<String> temp = mySQL.getActiveChannels();
            for (String channel : temp) if (!channels.contains(channel)) channels.add(channel);
        }

        // Init Bot
        botClient = new BotClient(botName, botToken, prefix, channels, mySQL);
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