package utility.command;

import utility.calculate.Calculate;
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
            List<Integer> keys =
                    getCollectionManager().getTreeMap().keySet().stream()
                            .filter(workerKey -> workerKey < key)
                            .toList();
            for (int key1 : keys)
                getCollectionManager().getTreeMap().remove(key1);
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
     * Конструктор задает менеджер для работы с коллекцией.
     *
     * @param collectionManager менеджер для работы с коллекцией
     */
    public RemoveLowerKeyCommand(CollectionManager collectionManager) {
        setCollectionManager(collectionManager);
    }
}
