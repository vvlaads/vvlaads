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
    private final Map<String, Command> commands = new HashMap<>();
    /**
     * List, хранящий команды с аргументами.
     */
    private final List<Command> commandsWithArguments = new ArrayList<>();
    /**
     * Менеджер для работы с коллекцией.
     */
    private CollectionManager collectionManager;
    /**
     * Сканер, через который поступают команды.
     */
    private Scanner scanner;

    /**
     * Возвращает сканер, через который поступают команды.
     *
     * @return сканер, через который поступают команды
     */
    public Scanner getScanner() {
        return scanner;
    }

    /**
     * Устанавливает сканер, через который поступают команды.
     *
     * @param scanner сканер, через который поступают команды
     */
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Возвращает менеджер для работы с коллекцией.
     *
     * @return менеджер для работы с коллекцией
     */
    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    /**
     * Устанавливает менеджер для работы с коллекцией.
     *
     * @param collectionManager менеджер для работы с коллекцией
     */
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Добавляет команду в коллекцию всех команд и в коллекцию команд с аргументами, если команда имеет аргумент.
     *
     * @param command команда для добавления
     */
    public void addCommand(Command command) {
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
    public List<Command> getCommands() {
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
    public Command getCommand(String name) {
        return commands.get(name);
    }

    /**
     * Выполнение команды, переданной в строке.
     *
     * @param line строка, содержащая команду
     */

    public void execute(String line) {
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
    public void addAllCommands() {
        addCommand(new ExitCommand());
        addCommand(new HelpCommand(this));
        addCommand(new SaveCommand(getCollectionManager()));
        addCommand(new InfoCommand(getCollectionManager()));
        addCommand(new ShowCommand(getCollectionManager()));
        addCommand(new ClearCommand(getCollectionManager()));
        addCommand(new InsertCommand(getCollectionManager(), this));
        addCommand(new UpdateCommand(getCollectionManager(), this));
        addCommand(new RemoveKeyCommand(getCollectionManager()));
        addCommand(new ExecuteScriptCommand(getCollectionManager()));
        addCommand(new RemoveGreaterCommand(getCollectionManager(), this));
        addCommand(new ReplaceIfLoweCommand(getCollectionManager(), this));
        addCommand(new RemoveLowerKeyCommand(getCollectionManager()));
        addCommand(new AverageOfSalaryCommand(getCollectionManager()));
        addCommand(new MinByStatusCommand(getCollectionManager()));
        addCommand(new PrintDescendingCommand(getCollectionManager()));
    }

    /**
     * Конструктор задает менеджер для работы с коллекцией и сканер, через который поступают команды.
     *
     * @param collectionManager менеджер для работы с коллекцией
     * @param scanner           сканер, через который поступают команды
     */
    public CommandManager(CollectionManager collectionManager, Scanner scanner) {
        setCollectionManager(collectionManager);
        setScanner(scanner);
    }
}
