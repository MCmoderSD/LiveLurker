package de.MCmoderSD.commands;

import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

import de.MCmoderSD.core.CommandHandler;

import de.MCmoderSD.utilities.database.MySQL;

import static de.MCmoderSD.utilities.other.Calculate.*;

public class Ping {

    // Constructor
    public Ping(MySQL mySQL, CommandHandler commandHandler, TwitchChat chat, String botName) {

        // About
        String[] name = {"livelurkerping", "livelurkerlatency"};


        // Register command
        commandHandler.registerCommand(new Command(name) {
            @Override
            public void execute(ChannelMessageEvent event, String... args) {

                // Check Permissions
                if (!getAuthor(event).equals(botName)) return;

                // Check latency
                String response = "Pong " + chat.getLatency() + "ms";

                // Send Message
                chat.sendMessage(getChannel(event), response);

                // Log response
                mySQL.logResponse(event, getCommand(), processArgs(args), response);
            }
        });
    }
}
