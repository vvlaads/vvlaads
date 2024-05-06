package command;

import manager.CollectionManager;
import worker.Worker;

import java.util.Map;

/**
 * Команда для подсчёта средней зарплаты.
 */
public class AverageOfSalaryCommand implements Command {
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
     * @param args аргументы команды
     */
    public void execute(String args) {
        int count = getCollectionManager().getTreeMap().size();
        Long sumOfSalary = 0L;
        for (Map.Entry<Integer, Worker> entry : getCollectionManager().getTreeMap().entrySet()) {
            Worker worker = entry.getValue();
            if (worker.getSalary() != null) {
                sumOfSalary += worker.getSalary();
            }
        }
        if (count == 0) {
            System.out.println("Невозможно посчитать среднюю зарплату, т.к. отсутствуют работники в коллекции");
        } else {
            double result = (double) sumOfSalary / count;
            System.out.println("Средняя зарплата: " + result);
        }

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
