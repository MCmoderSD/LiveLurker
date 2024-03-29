package de.MCmoderSD.commands;

import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

import de.MCmoderSD.core.CommandHandler;

import de.MCmoderSD.utilities.database.MySQL;

import static de.MCmoderSD.utilities.other.Calculate.*;

public class Status {

    // Constructor
    public Status(MySQL mySQL, CommandHandler commandHandler, TwitchChat chat, String botName) {

        // About
        String[] name = {"livelurkerstatus", "livelurkertest"};


        // Register command
        commandHandler.registerCommand(new Command(name) {
            @Override
            public void execute(ChannelMessageEvent event, String... args) {
                if (!getAuthor(event).equals(botName)) return;
                String response = "LiveLurker ready!";

                // Send message
                chat.sendMessage(getChannel(event), response);
                mySQL.logResponse(event, getCommand(), processArgs(args), response);
            }
        });
    }
}