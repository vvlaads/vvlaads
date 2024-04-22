package command;

import calculate.CreateWorker;
import calculate.Calculate;
import manager.CollectionManager;
import worker.Worker;


/**
 * Команда для добавления работника в коллекцию.
 */
public class InsertCommand implements Command {
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
        return "insert";
    }

    /**
     * Добавление работника в коллекцию по переданному ключу.
     *
     * @param args ключ
     */
    public void execute(String args) {
        if (!Calculate.stringIsInteger(args)) {
            System.out.println("Неверное значение аргумента");
        } else {
            Integer key = Integer.parseInt(args);
            if (CollectionManager.hasKey(key)) {
                System.out.println("Объект с таким ключом уже существует");
            } else {
                Worker worker = CreateWorker.createWorker();
                CollectionManager.getTreeMap().put(key, worker);
                System.out.println("Объект успешно добавлен");
            }
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "insert null {element}: добавить новый элемент с заданным ключом";
    }
}
