package utility.command;

import utility.worker.Worker;
import utility.manager.CollectionManager;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда для вывода информации о коллекции.
 */
public class InfoCommand implements Command, Serializable {
    /**
     * Поле для сериализация.
     */
    @Serial
    private static final long serialVersionUID = 106L;
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
        return "info";
    }

    /**
     * Вывод информации о коллекции работников.
     *
     * @param argument аргументы команды
     */
    public String execute(Argument argument, Worker worker) {
        return "Тип коллекции: " + getCollectionManager().getTreeMap().getClass() + "\n"
                + "Дата создания коллекции: " + getCollectionManager().getDateCreated() + "\n"
                + "Количество элементов: " + getCollectionManager().getTreeMap().size();
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "info: вывести в стандартный поток вывода информацию о коллекции " +
                "(тип, дата инициализации, количество элементов и т.д.)";
    }

    /**
     * Конструктор задает менеджер для работы с коллекцией.
     *
     * @param collectionManager менеджер для работы с коллекцией
     */
    public InfoCommand(CollectionManager collectionManager) {
        setCollectionManager(collectionManager);
    }
}
