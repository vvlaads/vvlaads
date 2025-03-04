package command;

import manager.CollectionManager;
import worker.Status;
import worker.Worker;

import java.util.Map;

/**
 * Команда для вывода работника с минимальным статусом.
 */
public class MinByStatusCommand implements Command {
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
     * @param args аргументы команды
     */
    public void execute(String args) {
        Worker newWorker = null;
        Status status = Status.REGULAR;
        for (Map.Entry<Integer, Worker> entry : getCollectionManager().getTreeMap().entrySet()) {
            Worker worker = entry.getValue();
            if (worker.getStatus() != null) {
                if (worker.getStatus().compareTo(status) < 0) {
                    status = worker.getStatus();
                    newWorker = worker;
                }
            }
        }
        if (newWorker != null) {
            System.out.println(newWorker);
        } else {
            System.out.println("В коллекции отсутствуют работники со статусом");
        }
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
