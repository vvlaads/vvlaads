package utility.command;

import utility.calculate.Calculate;
import utility.manager.CommandManager;
import utility.worker.Worker;
import utility.manager.CollectionManager;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Команда для удаления работников из коллекции с меньшим ключом.
 */
public class RemoveLowerKeyCommand implements Command, Serializable {
    /**
     * Поле для сериализация.
     */
    @Serial
    private static final long serialVersionUID = 112L;
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
        return true;
    }

    /**
     * Возвращает название команды.
     *
     * @return название команды
     */
    public String getName() {
        return "remove_lower_key";
    }

    /**
     * Удаление работников из коллекции с ключом меньше заданного.
     *
     * @param argument ключ
     */
    public String execute(Argument argument, Worker worker) {
        if (!Calculate.stringIsInteger(argument.getName())) {
            return ("Ошибка! Неверно введён ключ");
        } else {
            Integer key = Integer.parseInt(argument.getName());
            List<Integer> keys = getCollectionManager().getKeySet().stream()
                    .filter(workerKey -> workerKey < key)
                    .toList();
            for (int key1 : keys) {
                Worker worker1 = getCollectionManager().get(key1);
                if (getCommandManager().getDatabaseManager().removeWorker(worker1, getCommandManager().getClientData())) {
                    getCollectionManager().remove(key1);
                }
            }
            return ("Объекты успешно удалены");
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "remove_lower_key null : удалить из коллекции все элементы, ключ которых меньше, чем заданный";
    }

    /**
     * Конструктор задает менеджеры для работы с коллекцией и командами.
     *
     * @param collectionManager менеджер для работы с коллекцией
     * @param commandManager    менеджер для работы с командами
     */
    public RemoveLowerKeyCommand(CollectionManager collectionManager, CommandManager commandManager) {
        setCollectionManager(collectionManager);
        setCommandManager(commandManager);
    }
}
