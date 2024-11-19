package command;

import manager.CollectionManager;

/**
 * Команда для удаления работника из коллекции по ключу.
 */
public class RemoveKeyCommand implements Command {
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
        return "remove_key";
    }

    /**
     * Удаление работника с выбранным ключом из коллекции.
     *
     * @param args ключ
     */
    public void execute(String args) {
        Integer key = Integer.parseInt(args);
        getCollectionManager().getTreeMap().remove(key);
        System.out.println("Объект с выбранным ключом удалён из коллекции");
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "remove_key null: удалить элемент из коллекции по его ключу";
    }

    /**
     * Конструктор задает менеджер для работы с коллекцией.
     *
     * @param collectionManager менеджер для работы с коллекцией
     */
    public RemoveKeyCommand(CollectionManager collectionManager) {
        setCollectionManager(collectionManager);
    }
}
