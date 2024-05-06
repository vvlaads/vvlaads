package command;

import calculate.Calculate;
import manager.CollectionManager;

/**
 * Команда для удаления работников из коллекции с меньшим ключом.
 */
public class RemoveLowerKeyCommand implements Command {
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
        return true;
    }

    /**
     * Возвращает название команды.
     *
     * @return название команды
     */
    public String getName() {
        return "remove_lower_key";
    }

    /**
     * Удаление работников из коллекции с ключом меньше заданного.
     *
     * @param args ключ
     */
    public void execute(String args) {
        if (!Calculate.stringIsInteger(args)) {
            System.out.println("Ошибка! Неверно введён ключ");
        } else {
            Integer key = Integer.parseInt(args);
            Integer lowerKey = getCollectionManager().getTreeMap().lowerKey(key);
            while (lowerKey != null) {
                getCollectionManager().getTreeMap().remove(lowerKey);
                lowerKey = getCollectionManager().getTreeMap().lowerKey(key);
            }
            System.out.println("Объекты успешно удалены");
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "remove_lower_key null : удалить из коллекции все элементы, ключ которых меньше, чем заданный";
    }

    /**
     * Конструктор задает менеджер для работы с коллекцией.
     *
     * @param collectionManager менеджер для работы с коллекцией
     */
    public RemoveLowerKeyCommand(CollectionManager collectionManager) {
        setCollectionManager(collectionManager);
    }
}
