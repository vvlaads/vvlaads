package command;

import manager.CollectionManager;

/**
 * Команда для очистки коллекции рабочих.
 */
public class ClearCommand implements Command {
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
        return "clear";
    }

    /**
     * Очистка коллекции рабочих.
     *
     * @param args аргументы команды
     */
    public void execute(String args) {
        CollectionManager.getTreeMap().clear();
        System.out.println("Коллекция успешно очищена");
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "clear: очистить коллекцию";
    }
}
