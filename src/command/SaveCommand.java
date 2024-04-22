package command;

import manager.CollectionManager;

/**
 * Команда для сохранения коллекции в файл.
 */
public class SaveCommand implements Command {
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
        return "save";
    }

    /**
     * Сохранение текущей коллекции работников в файл.
     *
     * @param args аргументы команды
     */
    public void execute(String args) {
        CollectionManager.save();
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "save: сохранить коллекцию в файл";

    }
}
