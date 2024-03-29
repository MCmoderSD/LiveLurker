package de.MCmoderSD.commands;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import org.apache.commons.lang.IncompleteArgumentException;

public abstract class Command {

    // Attributes
    private final String command; // Name
    private final String[] alias; // Alias

    // Constructor
    public Command(String... command) {

        // Null Check
        if (command.length == 0) throw new IncompleteArgumentException("Command name missing!");

        // Set attributes
        this.command = command[0]; // Name
        this.alias = command; // Alias
        }

    // Methods
    public abstract void execute(ChannelMessageEvent event, String... args); // Execute the command

    // Getter
    public String getCommand() {
        return command;
    } // Get the command

    public String[] getAlias() {
        return alias;
    } // Get the alias
}