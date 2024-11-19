package utility.command;

import utility.worker.Worker;
import utility.manager.CollectionManager;
import utility.manager.CommandManager;

import java.io.Serial;
import java.io.Serializable;
import java.util.NoSuchElementException;

/**
 * Команда для обновления данных о работнике.
 */
public class UpdateCommand implements Command, Serializable {
    /**
     * Поле для сериализация.
     */
    @Serial
    private static final long serialVersionUID = 115L;
    /**
     * Менеджер для работы с коллекцией.
     */
    private CollectionManager collectionManager;
    /**
     * Менеджер для работы с командами.
     */
    private CommandManager commandManager;

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
     * Возвращает менеджер для работы с командами.
     *
     * @return менеджер для работы с командами
     */
    public CommandManager getCommandManager() {
        return commandManager;
    }

    /**
     * Устанавливает менеджер для работы с командами.
     *
     * @param commandManager менеджер для работы с командами
     */
    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    /**
     * /**
     * Возвращает
     * <ul>
     *  <li>true, если команда имеет аргументы</li>
     *  <li>false, если команда не имеет аргументов</li>
     * </ul>
     *
     * @return <ul>
     *  <li>true, если команда имеет аргументы</li>
     *  <li>false, если команда не имеет аргументов</li>
     * </ul>
     */
    public boolean hasArguments() {
        return true;
    }

    /**
     * Возвращает название команды.
     *
     * @return название команды
     */
    public String getName() {
        return "update";
    }

    /**
     * Обновить данные о работнике с переданным ID.
     *
     * @param argument ID работника
     */
    public String execute(Argument argument, Worker newWorker) {
        long id = Long.parseLong(argument.getName());
        if (!getCollectionManager().hasID(id)) {
            return ("Работника с таким ID не существует");
        } else {
            try {
                getCollectionManager().getTreeMap().entrySet().stream()
                        .filter(worker -> worker.getValue().getId() == id).
                        forEach(worker -> getCollectionManager().getTreeMap().put(worker.getKey(), worker.getValue()));
                return ("Объект успешно обновлен");
            } catch (NoSuchElementException e) {
                return ("Не удалось обновить объект");
            }
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "update id {element}: обновить значение элемента коллекции, id которого равен заданному";
    }

    /**
     * Конструктор задает менеджер для работы с коллекцией и менеджер для работы с командами.
     *
     * @param collectionManager менеджер для работы с коллекцией
     * @param commandManager    менеджер для работы с командами
     */
    public UpdateCommand(CollectionManager collectionManager, CommandManager commandManager) {
        setCollectionManager(collectionManager);
        setCommandManager(commandManager);
    }
}
