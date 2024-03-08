package de.MCmoderSD.commands;

import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import de.MCmoderSD.core.CommandHandler;

import static de.MCmoderSD.utilities.other.Calculate.getAuthor;
import static de.MCmoderSD.utilities.other.Calculate.getChannel;

public class Status {

    // Constructor
    public Status(CommandHandler commandHandler, TwitchChat chat, String username) {

        // Register command
        commandHandler.registerCommand(new Command("status") { // Command name and aliases
            @Override
            public void execute(ChannelMessageEvent event, String... args) {
                if (getAuthor(event).equals(username.toLowerCase()))
                    chat.sendMessage(getChannel(event), "LiveLurker ready!");
            }
        });
    }
}