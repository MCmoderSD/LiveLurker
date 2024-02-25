package de.MCmoderSD.main;

import de.MCmoderSD.core.BotClient;
import de.MCmoderSD.utilities.json.JsonNode;
import de.MCmoderSD.utilities.json.JsonUtility;

public class Main {
    public Main() {
        JsonUtility jsonUtility = new JsonUtility();

        JsonNode botConfig = jsonUtility.load("/config/BotConfig.json");

        // Load Name
        String botName = botConfig.get("botName").asText();

        // Load token
        String botToken = botConfig.get("botToken").asText();

        // Load prefix
        String prefix = botConfig.get("prefix").asText();

        // Load Channel List
        JsonNode channelList = jsonUtility.load("/config/ChannelList.json");
        String[] channels = new String[channelList.getSize()];
        for (int i = 0; i < channelList.getSize(); i++) channels[i] = channelList.get("#" + i).asText();

        // Init Bot
        new BotClient(botName, botToken, prefix, channels);
    }

    public static void main(String[] args) {
        new Main();
    }
}
