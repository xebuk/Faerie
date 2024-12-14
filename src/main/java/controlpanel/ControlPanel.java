package controlpanel;

import multithreading.BotThread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ControlPanel {
    private static final Map<String, Command> commands = new HashMap<>();

    private static BotThread faerie = new BotThread();

    static {
        ControlPanel.registerCommand(
                "toggle",
                List.of("tg", "tgb", "toggle_bot"),
                Map.of(),
                (Map<String, String> options) -> {
                    faerie.run();
                }
        );

        ControlPanel.registerCommand(
                "stop",
                List.of("exit", "ex"),
                Map.of(),
                (Map<String, String> options) -> {
                    System.exit(0);
                }
        );
    }

    static class Command {
        String name;
        List<String> aliases;
        Map<String, Boolean> options;       // option -> requires argument?
        Consumer<Map<String, String>> action;

        public Command(String name, List<String> aliases, Map<String, Boolean> options, Consumer<Map<String, String>> action) {
            this.name = name;
            this.aliases = aliases;
            this.options = options;
            this.action = action;
        }

        boolean matches(String input) {
            return name.equals(input) || aliases.contains(input);
        }
    }

    public static void registerCommand(String name, List<String> aliases, Map<String, Boolean> options, Consumer<Map<String, String>> action) {
        commands.put(name, new Command(name, aliases, options, action));
    }

    public static void parseInput(String input) {
        String[] tokens = input.split("\\s+");
        if (tokens.length == 0) {
            return;
        }

        String commandName = tokens[0];
        Command command = findCommand(commandName);
        if (command == null) {
            System.out.println("Unknown command: " + commandName);
            return;
        }

        Map<String, String> options = new HashMap<>();
        int i = 1;
        while (i < tokens.length) {
            String token = tokens[i];
            if (!token.startsWith("-")) {
                System.out.println("Unexpected token: " + token);
                return;
            }

            if (!command.options.containsKey(token)) {
                System.out.println("Unknown option: " + token);
                return;
            }

            boolean requiresArgument = command.options.get(token);
            if (requiresArgument) {
                if (i + 1 >= tokens.length || tokens[i + 1].startsWith("-")) {
                    System.out.println("Option '" + token + "' requires an argument");
                    return;
                }
                options.put(tokens[i], tokens[i + 1]);
                i++;
            } else {
                options.put(token, null);
            }
            i++;
        }

        command.action.accept(options);
    }

    private static Command findCommand(String command) {
        for (Command cmd : commands.values()) {
            if (cmd.matches(command)) {
                return cmd;
            }
        }
        return null;
    }
}
