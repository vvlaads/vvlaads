package utility.command;

import utility.comparators.WorkerStatusComparator;
import utility.worker.Worker;
import utility.manager.CollectionManager;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда для вывода работника с минимальным статусом.
 */
public class MinByStatusCommand implements Command, Serializable {
    /**
     * Поле для сериализация.
     */
    @Serial
    private static final long serialVersionUID = 108L;
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
        return "min_by_status";
    }

    /**
     * Вывод работника из коллекции с минимальным статусом.
     *
     * @param argument аргументы команды
     */
    public String execute(Argument argument, Worker w) {
        Worker newWorker = getCollectionManager().getValues().stream()
                .filter(worker -> worker.getStatus() != null)
                .sorted(new WorkerStatusComparator()).toList().get(0);
        return String.valueOf(newWorker);
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "min_by_status : вывести любой объект из коллекции, значение поля status которого является минимальным";
    }

    /**
     * Конструктор задает менеджер для работы с коллекцией.
     *
     * @param collectionManager менеджер для работы с коллекцией
     */
    public MinByStatusCommand(CollectionManager collectionManager) {
        setCollectionManager(collectionManager);
    }
}
