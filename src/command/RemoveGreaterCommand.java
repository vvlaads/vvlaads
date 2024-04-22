package command;

import calculate.CreateWorker;
import manager.CollectionManager;
import worker.Worker;

/**
 * Команда для удаления работников из коллекции, превышающих выбранного.
 */
public class RemoveGreaterCommand implements Command {
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
        Worker worker = CreateWorker.createWorker();
        CollectionManager.removeGreater(worker);
        System.out.println("Объекты успешно удалены");
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный";
    }
}
