package command;

import manager.CollectionManager;

/**
 * Команда для вывода списка работников в обратном порядке.
 */
public class PrintDescendingCommand implements Command {
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
     * @param args аргументы команды
     */
    public void execute(String args) {
        CollectionManager.getDescendingWorkers();
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "print_descending : вывести элементы коллекции в порядке убывания";
    }
}
