package manager;

import command.*;

import java.util.*;

/**
 * Класс для взаимодействия с командами.
 */
public class CommandManager {
    /**
     * HashMap, хранящая все команды.
     */
    private static final Map<String, Command> commands = new HashMap<>();
    /**
     * List, хранящий команды с аргументами.
     */
    private static final List<Command> commandsWithArguments = new ArrayList<>();

    /**
     * Добавляет команду в коллекцию всех команд и в коллекцию команд с аргументами, если команда имеет аргумент.
     *
     * @param command команда для добавления
     */
    public static void addCommand(Command command) {
        commands.put(command.getName(), command);
        if (command.hasArguments()) {
            commandsWithArguments.add(command);
        }
    }

    /**
     * Возвращает коллекцию List со всеми командами.
     *
     * @return коллекция List со всеми командами
     */
    public static List<Command> getCommands() {
        List<Command> values = new ArrayList<>();
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            Command value = entry.getValue();
            values.add(value);
        }
        return values;
    }

    /**
     * Возвращает команду с переданным названием.
     *
     * @param name название команды
     * @return команда с переданным названием
     */
    public static Command getCommand(String name) {
        return commands.get(name);
    }

    /**
     * Выполнение команды, переданной в строке.
     *
     * @param line строка, содержащая команду
     */

    public static void execute(String line) {
        String[] tokens = line.split(" ");
        Command command = getCommand(tokens[0]);
        if (command != null) {
            String commandArguments = "";
            if (commandsWithArguments.contains(command)) {
                if (tokens.length == 2) {
                    commandArguments = tokens[1];
                    command.execute(commandArguments);
                } else {
                    System.out.println("Неверный ввод аргумента для выбранной команды!");
                }
            } else if (tokens.length > 1) {
                System.out.println("У выбранной команды отсутствуют аргументы!");
            } else {
                command.execute(commandArguments);
            }
        } else {
            System.out.println("Неверно введена команда!");
        }
    }

    /**
     * Добавляет команды в коллекцию всех команд и в коллекцию команд с аргументами, если команда имеет аргумент.
     */
    public static void addAllCommands() {
        addCommand(new HelpCommand());
        addCommand(new InfoCommand());
        addCommand(new ShowCommand());
        addCommand(new InsertCommand());
        addCommand(new UpdateCommand());
        addCommand(new RemoveKeyCommand());
        addCommand(new ClearCommand());
        addCommand(new SaveCommand());
        addCommand(new ExecuteScriptCommand());
        addCommand(new ExitCommand());
        addCommand(new RemoveGreaterCommand());
        addCommand(new ReplaceIfLoweCommand());
        addCommand(new RemoveLowerKeyCommand());
        addCommand(new AverageOfSalaryCommand());
        addCommand(new MinByStatusCommand());
        addCommand(new PrintDescendingCommand());
    }
}