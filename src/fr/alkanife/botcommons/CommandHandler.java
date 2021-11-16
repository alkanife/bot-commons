package fr.alkanife.botcommons;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class CommandHandler {

    private Map<String, BotCommand> commands = new HashMap<>();

    public Collection<BotCommand> getCommands() {
        return commands.values();
    }

    public void registerCommands(Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                Command command = method.getAnnotation(Command.class);

                method.setAccessible(true);

                BotCommand simpleCommand = new BotCommand(command.name(), object, method);

                commands.put(command.name(), simpleCommand);
            }
        }
    }

    public void handle(SlashCommandEvent slashCommandEvent) {
        try {
            BotCommand botCommand = getCommand(slashCommandEvent.getName().toLowerCase(Locale.ROOT));

            if (botCommand == null)
                return;

            Parameter[] parameters = botCommand.getMethod().getParameters();

            if (parameters.length != 1)
                return;

            if (parameters[0].getType().equals(SlashCommandEvent.class)) {
                botCommand.getMethod().invoke(botCommand.getObject(), slashCommandEvent);

                success(slashCommandEvent);
            }
        } catch (Exception exception) {
            fail(slashCommandEvent, exception);
        }
    }

    public abstract void success(SlashCommandEvent slashCommandEvent);

    public abstract void fail(SlashCommandEvent slashCommandEvent, Exception exception);

    public BotCommand getCommand(String commandName) {
        return commands.get(commandName);
    }
}