package command;

import calculate.CreateWorker;
import manager.CollectionManager;
import manager.CommandManager;
import worker.Worker;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Команда для обновления данных о работнике.
 */
public class UpdateCommand implements Command {
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
     * @param args ID работника
     */
    public void execute(String args) {
        long id = Long.parseLong(args);
        if (!getCollectionManager().hasID(id)) {
            System.out.println("Работника с таким ID не существует");
        } else {
            try {
                CreateWorker createWorker = new CreateWorker();
                Worker newWorker = createWorker.createWorker(getCommandManager().getScanner());
                for (Map.Entry<Integer, Worker> entry : getCollectionManager().getTreeMap().entrySet()) {
                    Integer key = entry.getKey();
                    Worker oldWorker = entry.getValue();
                    if (oldWorker.getId() == id) {
                        getCollectionManager().getTreeMap().put(key, newWorker);
                        break;
                    }
                }
                System.out.println("Объект успешно обновлен");
            } catch (NoSuchElementException e) {
                System.out.println("Не удалось обновить объект");
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
