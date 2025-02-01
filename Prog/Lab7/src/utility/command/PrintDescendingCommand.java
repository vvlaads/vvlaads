package utility.command;

import utility.comparators.DescendingWorkerComparator;
import utility.worker.Worker;
import utility.manager.CollectionManager;

import java.io.Serial;
import java.io.Serializable;
import java.util.stream.Collectors;

/**
 * Команда для вывода списка работников в обратном порядке.
 */
public class PrintDescendingCommand implements Command, Serializable {
    /**
     * Поле для сериализация.
     */
    @Serial
    private static final long serialVersionUID = 109L;
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
        return "print_descending";
    }

    /**
     * Вывод списка работников коллекции в обратном порядке.
     *
     * @param argument аргументы команды
     */
    public String execute(Argument argument, Worker worker) {
        return getCollectionManager().getValues().stream()
                .sorted(new DescendingWorkerComparator())
                .map(Worker::toString)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "print_descending : вывести элементы коллекции в порядке убывания";
    }

    /**
     * Конструктор задает менеджер для работы с коллекцией.
     *
     * @param collectionManager менеджер для работы с коллекцией
     */
    public PrintDescendingCommand(CollectionManager collectionManager) {
        setCollectionManager(collectionManager);
    }
}
