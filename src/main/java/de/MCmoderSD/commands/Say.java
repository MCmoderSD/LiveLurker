package de.MCmoderSD.commands;

import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

import de.MCmoderSD.core.CommandHandler;

import de.MCmoderSD.utilities.database.MySQL;

import static de.MCmoderSD.utilities.other.Calculate.*;

public class Say {

    // Constructor
    public Say(MySQL mySQL, CommandHandler commandHandler, TwitchChat chat, String botName) {

        // About
        String[] name = {"livelurkersay", "livelurkerrepeat"};


        // Register command
        commandHandler.registerCommand(new Command(name) {
            @Override
            public void execute(ChannelMessageEvent event, String... args) {

                // Check Permissions
                if (!getAuthor(event).equals(botName) && args.length == 0) return;

                // Build Response
                String response = processArgs(args);

                // Send Message and log
                chat.sendMessage(getChannel(event), response);
                mySQL.logResponse(event, getCommand(), processArgs(args), response);
            }
        });
    }
}