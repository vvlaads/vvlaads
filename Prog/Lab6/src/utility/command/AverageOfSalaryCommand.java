package utility.command;

import utility.worker.Worker;
import utility.manager.CollectionManager;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда для подсчёта средней зарплаты.
 */
public class AverageOfSalaryCommand implements Command, Serializable {
    /**
     * Поле для сериализация.
     */
    @Serial
    private static final long serialVersionUID = 101L;
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
        return "average_of_salary";
    }

    /**
     * Вывод средней зарплаты рабочих.
     *
     * @param argument аргументы команды
     */
    public String execute(Argument argument, Worker newWorker) {
        int count = getCollectionManager().getTreeMap().values().size();
        long sum = getCollectionManager().getTreeMap().values().stream()
                .filter(worker -> worker.getSalary() != null)
                .mapToLong(Worker::getSalary).sum();

        double result = (double) sum / count;
        return ("Средняя зарплата: " + result);
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "average_of_salary : вывести среднее значение поля salary для всех элементов коллекции";
    }

    /**
     * Конструктор задает менеджер для работы с коллекцией.
     *
     * @param collectionManager менеджер для работы с коллекцией
     */
    public AverageOfSalaryCommand(CollectionManager collectionManager) {
        setCollectionManager(collectionManager);
    }
}
