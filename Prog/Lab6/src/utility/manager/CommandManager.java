package utility.manager;

import utility.calculate.CreateWorker;
import utility.command.*;
import utility.worker.Worker;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

/**
 * Класс для взаимодействия с командами.
 */
public class CommandManager implements Serializable {
    /**
     * Поле для сериализации.
     */
    @Serial
    private final static long serialVersionUID = 2001L;
    /**
     * HashMap, хранящая все команды.
     */
    private final Map<String, Command> commands = new HashMap<>();
    /**
     * List, хранящий команды с аргументами.
     */
    private final List<Command> commandsWithArguments = new ArrayList<>();
    /**
     * List, хранящий команды, требующие создания работника.
     */
    private final List<String> commandsWithCreateWorker = new ArrayList<>
            (Stream.of("insert", "update", "replace_if_lowe", "remove_greater").toList());
    /**
     * Менеджер для работы с коллекцией.
     */
    private CollectionManager collectionManager;

    /**
     * Возвращает список команд, требующих создания работника.
     *
     * @return список команд, требующих создания работника
     */
    public List<String> getCommandsWithCreateWorker() {
        return commandsWithCreateWorker;
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
     * Возвращает список команд с аргументами.
     *
     * @return список команд с аргументами
     */
    public List<Command> getCommandsWithArguments() {
        return commandsWithArguments;
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
     * @param line    строка, содержащая команду
     * @param scanner сканер, с которого считываются данные
     * @return сообщение о выполнении команды
     */

    public String execute(String line, Scanner scanner) {
        if (!line.isBlank()) {
            String[] tokens = line.split(" ");
            Command command = getCommand(tokens[0]);
            if (command != null) {
                Argument argument = new Argument();
                if (getCommandsWithArguments().contains(command)) {
                    if (tokens.length == 2) {
                        argument.setName(tokens[1]);
                        Worker worker = new Worker();
                        if (getCommandsWithCreateWorker().contains(command.getName())) {
                            CreateWorker createWorker = new CreateWorker();
                            worker = createWorker.createWorker(scanner);
                        }
                        return command.execute(argument, worker);
                    } else {
                        return ("Неверный ввод аргумента для выбранной команды!");
                    }
                } else if (tokens.length > 1) {
                    return ("У выбранной команды отсутствуют аргументы!");
                } else {
                    Worker worker = new Worker();
                    if (getCommandsWithCreateWorker().contains(command.getName())) {
                        CreateWorker createWorker = new CreateWorker();
                        worker = createWorker.createWorker(scanner);
                    }
                    return command.execute(argument, worker);
                }
            } else {
                return ("Неверно введена команда!");
            }
        }
        return "";
    }


    /**
     * Добавляет команды в коллекцию всех команд и в коллекцию команд с аргументами, если команда имеет аргумент.
     */
    public void addAllCommands() {
        addCommand(new ExitCommand());
        addCommand(new HelpCommand(this));
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
     */
    public CommandManager(CollectionManager collectionManager) {
        setCollectionManager(collectionManager);
    }
}
