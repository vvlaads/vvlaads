package command;

import manager.CollectionManager;
import worker.Worker;

/**
 * Команда для вывода работника с минимальным статусом.
 */
public class MinByStatusCommand implements Command {
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
        Worker worker = CollectionManager.minStatus();
        if (worker != null) {
            System.out.println(worker);
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
}
