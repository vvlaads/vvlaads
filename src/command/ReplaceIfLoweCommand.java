package command;

import calculate.Calculate;
import calculate.CreateWorker;
import manager.CollectionManager;
import worker.Worker;

/**
 * Команда для замены работника, если он меньше.
 */
public class ReplaceIfLoweCommand implements Command {
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
        return "replace_if_lowe";
    }

    /**
     * Замена работника из коллекции по выбранному ключу, если он меньше заданного.
     *
     * @param args ключ
     */
    public void execute(String args) {
        if (!Calculate.stringIsInteger(args)) {
            System.out.println("Неверное значение аргумента");
        } else {
            Integer key = Integer.parseInt(args);
            if (!CollectionManager.getTreeMap().containsKey(key)) {
                System.out.println("Нет объекта с выбранным ключом");
            } else {
                Worker worker = CreateWorker.createWorker();
                if (worker.compareTo(CollectionManager.getTreeMap().get(key)) < 0) {
                    CollectionManager.getTreeMap().put(key, worker);
                    System.out.println("Объект успешно изменён");
                } else {
                    System.out.println("Объект не был изменён");
                }
            }
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "replace_if_lowe null {element} : заменить значение по ключу, если новое значение меньше старого";
    }
}
