package command;

import calculate.CreateWorker;
import manager.CollectionManager;
import manager.CommandManager;
import worker.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Команда для удаления работников из коллекции, превышающих выбранного.
 */
public class RemoveGreaterCommand implements Command {
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
     * @param args аргументы команды
     */
    public void execute(String args) {
        try {
            CreateWorker createWorker = new CreateWorker();
            Worker newWorker = createWorker.createWorker(getCommandManager().getScanner());
            List<Integer> keys = new ArrayList<>();
            for (Map.Entry<Integer, Worker> entry : getCollectionManager().getTreeMap().entrySet()) {
                Integer key = entry.getKey();
                Worker oldWorker = entry.getValue();
                if (oldWorker.compareTo(newWorker) > 0) {
                    keys.add(key);
                }
            }
            for (Integer key : keys) {
                getCollectionManager().getTreeMap().remove(key);
            }
            System.out.println("Объекты успешно удалены");
        } catch (NoSuchElementException e) {
            System.out.println("Не удалось удалить объекты");
        }
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
