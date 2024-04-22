package command;

import calculate.CreateWorker;
import manager.CollectionManager;
import worker.Worker;

/**
 * Команда для обновления данных о работнике.
 */
public class UpdateCommand implements Command {
    /**
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
        return "update";
    }

    /**
     * Обновить данные о работнике с переданным ID.
     *
     * @param args ID работника
     */
    public void execute(String args) {
        long id = Long.parseLong(args);
        if (!CollectionManager.hasID(id)) {
            System.out.println("Работника с таким ID не существует");
        } else {
            Worker worker = CreateWorker.createWorker();
            CollectionManager.updateWorker(id, worker);
            System.out.println("Объект успешно обновлен");
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "update id {element}: обновить значение элемента коллекции, id которого равен заданному";
    }
}
