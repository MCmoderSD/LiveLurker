package de.MCmoderSD.commands;

import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

import de.MCmoderSD.core.CommandHandler;

import de.MCmoderSD.utilities.database.MySQL;

import static de.MCmoderSD.utilities.other.Calculate.*;

public class Status {

    // Constructor
    public Status(MySQL mySQL, CommandHandler commandHandler, TwitchChat chat, String username) {

        // About
        String[] name = {"status"};
        String description = "Shows if LiveLurker is working";


        // Register command
        commandHandler.registerCommand(new Command(description, name) {
            @Override
            public void execute(ChannelMessageEvent event, String... args) {
                if (!getAuthor(event).equals(username.toLowerCase())) return;
                String response = "LiveLurker ready!";

                // Send message
                chat.sendMessage(getChannel(event), response);

                // Log response
                mySQL.logResponse(event, getCommand(), processArgs(args), response);
            }
        });
    }
}