package de.MCmoderSD.commands;

import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import de.MCmoderSD.core.CommandHandler;

public class Status {
    public Status(CommandHandler commandHandler, TwitchChat chat, String botName) {
        commandHandler.registerCommand(new Command("status") {
            @Override
            public void execute(ChannelMessageEvent event, String... args) {
                if (event.getUser().getName().equals(botName.toLowerCase()))
                    chat.sendMessage(event.getChannel().getName(), "Live and ready to lurk!");
            }
        });
    }
}
