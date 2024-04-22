package command;

import manager.CollectionManager;

/**
 * Команда для вывода всех работников из коллекции.
 */
public class ShowCommand implements Command {
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
        return "show";
    }

    /**
     * Вывод в командную строку всех работников из коллекции.
     *
     * @param args аргументы команды
     */
    public void execute(String args) {
        CollectionManager.getWorkers();
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
