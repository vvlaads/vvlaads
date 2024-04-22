package command;

import manager.CollectionManager;

/**
 * Команда для вывода информации о коллекции.
 */
public class InfoCommand implements Command {
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
        return "info";
    }

    /**
     * Вывод информации о коллекции работников.
     *
     * @param args аргументы команды
     */
    public void execute(String args) {
        System.out.println("Тип коллекции: " + CollectionManager.getTreeMap().getClass());
        System.out.println("Дата создания коллекции: " + CollectionManager.getDateCreated());
        System.out.println("Количество элементов: " + CollectionManager.getTreeMap().size());
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
}
