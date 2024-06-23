package utility.command;

import utility.worker.Worker;
import utility.manager.CollectionManager;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда для очистки коллекции рабочих.
 */
public class ClearCommand implements Command, Serializable {
    /**
     * Поле для сериализация.
     */
    @Serial
    private static final long serialVersionUID = 102L;
    /**
     * Менеджер для работы с коллекцией.
     */
    private CollectionManager collectionManager;

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
        return "clear";
    }

    /**
     * Очистка коллекции рабочих.
     *
     * @param argument аргументы команды
     */
    public String execute(Argument argument, Worker worker) {
        getCollectionManager().getTreeMap().clear();
        return ("Коллекция успешно очищена");
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "clear: очистить коллекцию";
    }

    /**
     * Конструктор задает менеджер для работы с коллекцией.
     *
     * @param collectionManager менеджер для работы с коллекцией
     */
    public ClearCommand(CollectionManager collectionManager) {
        setCollectionManager(collectionManager);
    }
}
