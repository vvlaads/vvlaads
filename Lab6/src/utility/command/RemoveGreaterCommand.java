package utility.command;

import utility.manager.CollectionManager;
import utility.manager.CommandManager;
import utility.worker.Worker;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Команда для удаления работников из коллекции, превышающих выбранного.
 */
public class RemoveGreaterCommand implements Command, Serializable {
    /**
     * Поле для сериализация.
     */
    @Serial
    private static final long serialVersionUID = 110L;
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
        return false;
    }

    /**
     * Возвращает название команды.
     *
     * @return название команды
     */
    public String getName() {
        return "remove_greater";
    }

    /**
     * Удаление всех работников из коллекции, превышающих заданного.
     *
     * @param argument аргументы команды
     */
    public String execute(Argument argument, Worker newWorker) {
        List<Integer> keys = new ArrayList<>();
        getCollectionManager().getTreeMap().entrySet().stream()
                .filter(integerWorkerEntry -> integerWorkerEntry.getValue().compareTo(newWorker) > 0)
                .forEach(integerWorkerEntry -> keys.add(integerWorkerEntry.getKey()));
        for (int key1 : keys) {
            getCollectionManager().getTreeMap().remove(key1);
        }
        return ("Объекты успешно удалены");
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный";
    }

    /**
     * Конструктор задает менеджер для работы с коллекцией и менеджер для работы с командами.
     *
     * @param collectionManager менеджер для работы с коллекцией
     * @param commandManager    менеджер для работы с командами
     */
    public RemoveGreaterCommand(CollectionManager collectionManager, CommandManager commandManager) {
        setCollectionManager(collectionManager);
        setCommandManager(commandManager);
    }
}
