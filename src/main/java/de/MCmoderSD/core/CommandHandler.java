package de.MCmoderSD.core;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import de.MCmoderSD.commands.Command;

import java.util.HashMap;

public class CommandHandler {

    // Attributes
    private final HashMap<String, Command> commands;
    private final String prefix;

    // Constructor
    public CommandHandler(String prefix) {
        this.prefix = prefix;
        commands = new HashMap<>();
    }

    // Register a command
    public void registerCommand(Command command) {
        commands.put(command.getCommand(), command);
    }

    // Manually execute a command
    public void executeCommand(ChannelMessageEvent event, String command, String... args) {
        if (commands.containsKey(command)) getCommand(command).execute(event, args);
    }

    public void handleCommand(ChannelMessageEvent event) {
        String message = event.getMessage();

        // Check for prefix
        if (!message.startsWith(prefix)) return;

        // Convert message to command and arguments
        String[] split = message.split(" ");
        String command = split[0].substring(1);
        String[] args = new String[split.length - 1];
        System.arraycopy(split, 1, args, 0, split.length - 1);

        // Execute command
        executeCommand(event, command, args);

        // Log command execution
        System.out.printf("\033[0;1m<%s> Executed: %s\u001B[0m\n", event.getChannel().getName(), command);
    }

    // Setter and Getter
    public HashMap<String, Command> getCommands() {
        return commands;
    }

    public Command getCommand(String command) {
        return commands.get(command);
    }

    public void removeCommand(String command) {
        commands.remove(command);
    }
}
