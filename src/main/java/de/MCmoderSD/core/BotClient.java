package de.MCmoderSD.core;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.core.EventManager;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.eventsub.events.ChannelFollowEvent;
import com.github.twitch4j.eventsub.events.ChannelSubscribeEvent;

import de.MCmoderSD.commands.*;

import de.MCmoderSD.utilities.database.MySQL;
import de.MCmoderSD.utilities.json.JsonNode;
import de.MCmoderSD.utilities.json.JsonUtility;

import static de.MCmoderSD.utilities.other.Calculate.*;

public class BotClient {

    // Associations
    private final MySQL mySQL;

    // Attributes
    private final TwitchChat chat;
    private final CommandHandler commandHandler;
    private final String username;

    // Constructor
    public BotClient(String username, String token, String prefix, String[] channels, MySQL mySQL) {

        // Init Username
        this.username = username;

        // Init MySQL
        this.mySQL = mySQL;

        // Init Credential
        OAuth2Credential credential = new OAuth2Credential("twitch", token);

        // Init Client and Chat
        TwitchClient client = TwitchClientBuilder.builder()
                .withDefaultAuthToken(credential)
                .withChatAccount(credential)
                .withEnableChat(true)
                .withEnableHelix(true)
                .build();

        chat = client.getChat();

        // Init White and Blacklist
        JsonUtility jsonUtility = new JsonUtility();
        JsonNode whiteList = jsonUtility.load("/config/whitelist.json");
        JsonNode blackList = jsonUtility.load("/config/blacklist.json");

        // Init CommandHandler
        commandHandler = new CommandHandler(mySQL, whiteList, blackList, prefix);

        // Init Commands
        new Join(mySQL, commandHandler, chat);
        new Play(mySQL, commandHandler, chat);
        new Status(mySQL, commandHandler, chat, username);

        // Register the Bot into all channels
        new Thread(() -> {
            for (String channel : channels) {
                try {
                    chat.joinChannel(channel);
                    System.out.printf("%s%s %s Joined Channel: %s%s%s", BOLD, logTimestamp(), SYSTEM, channel, BREAK, UNBOLD);
                    Thread.sleep(250); // Prevent rate limit
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();

        // Init the EventListener
        EventManager eventManager = client.getEventManager();

        // Message Event
        eventManager.onEvent(ChannelMessageEvent.class, event -> {

            // Log to MySQL
            mySQL.logMessage(event);

            // Console Output
            System.out.printf("%s %s <%s> %s: %s%s", logTimestamp(), MESSAGE, getChannel(event), getAuthor(event), getMessage(event), BREAK);

            // Handle Command
            commandHandler.handleCommand(event);
        });

        // Follow Event
        eventManager.onEvent(ChannelFollowEvent.class, event -> System.out.printf("%s %s <%s> %s -> Followed%s", logTimestamp(), FOLLOW, event.getBroadcasterUserName(), event.getUserName(), BREAK));

        // Sub Event
        eventManager.onEvent(ChannelSubscribeEvent.class, event -> System.out.printf("%s %s <%s> %s -> Subscribed %s%s", logTimestamp(), SUBSCRIBE, event.getBroadcasterUserName(), event.getUserName(), event.getTier(), BREAK));
    }

    // Methods
    public void joinChannel(String channel) {
        chat.joinChannel(channel);
    }

    @SuppressWarnings("unused")
    public void leaveChannel(String channel) {
        chat.leaveChannel(channel);
    }

    public void sendMessage(String channel, String message) {
        if (!chat.getChannels().contains(channel)) joinChannel(channel);
        if (message.length() > 500) message = message.substring(0, 500);
        if (message.isEmpty()) return;

        chat.sendMessage(channel, message);
        System.out.printf("%s %s <%s> %s: %s%s", logTimestamp(), USER, channel, username, message, BREAK);
        mySQL.messageSent(channel, username, message);
    }
}