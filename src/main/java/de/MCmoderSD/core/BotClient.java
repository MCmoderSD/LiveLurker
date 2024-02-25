package de.MCmoderSD.core;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.core.EventManager;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import de.MCmoderSD.commands.Join;
import de.MCmoderSD.commands.Play;
import de.MCmoderSD.commands.Status;

public class BotClient {

    // Attributes
    private final TwitchClient client;
    private final TwitchChat chat;
    private final CommandHandler commandHandler;

    // Constructor
    public BotClient(String botName, String botToken, String prefix, String[] channels) {

        OAuth2Credential credential = new OAuth2Credential("twitch", botToken);

        // Init Client and Chat
        client = TwitchClientBuilder.builder()
                .withDefaultAuthToken(credential)
                .withChatAccount(credential)
                .withEnableChat(true)
                .withEnableHelix(true)
                .build();

        chat = client.getChat();

        // Register the Bot to the channel
        for (String channel : channels) {
            try {
                chat.joinChannel(channel);
                System.out.printf("\033[0;1mJoined Channel: %s\u001B[0m\n", channel);
                Thread.sleep(250); // Prevent rate limit
            } catch (InterruptedException e) {
                System.out.println("Error: " + e);
            }
        }

        // Init CommandHandler
        commandHandler = new CommandHandler(prefix);

        // Register commands
        new Status(commandHandler, chat, botName);
        new Play(commandHandler, chat);
        new Join(commandHandler, chat);

        // Init the EventListener
        EventManager eventManager = client.getEventManager();

        eventManager.onEvent(ChannelMessageEvent.class, event -> {

            // Console Output
            System.out.printf("[MSG] <%s> %s: %s\n", event.getChannel().getName(), event.getUser().getName(), event.getMessage());

            // Handle Command
            commandHandler.handleCommand(event);
        });
    }
}
