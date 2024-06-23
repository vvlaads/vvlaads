package utility.command;

import utility.calculate.Calculate;
import utility.worker.Worker;
import utility.manager.CollectionManager;
import utility.manager.CommandManager;

import java.io.Serial;
import java.io.Serializable;
import java.util.NoSuchElementException;

/**
 * Команда для замены работника, если он меньше.
 */
public class ReplaceIfLoweCommand implements Command, Serializable {
    /**
     * Поле для сериализация.
     */
    @Serial
    private static final long serialVersionUID = 113L;
    /**
     * Менеджер для работы с коллекцией.
     */
    private CollectionManager collectionManager;
    /**
     * Менеджер для работы с командами.
     */
    private CommandManager commandManager;

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
     * Возвращает менеджер для работы с командами.
     *
     * @return менеджер для работы с командами
     */
    public CommandManager getCommandManager() {
        return commandManager;
    }

    /**
     * Устанавливает менеджер для работы с командами.
     *
     * @param commandManager менеджер для работы с командами
     */
    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
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
        return "replace_if_lowe";
    }

    /**
     * Замена работника из коллекции по выбранному ключу, если он меньше заданного.
     *
     * @param argument ключ
     */
    public String execute(Argument argument, Worker worker) {
        if (!Calculate.stringIsInteger(argument.getName())) {
            return ("Неверное значение аргумента");
        } else {
            Integer key = Integer.parseInt(argument.getName());
            if (!getCollectionManager().getTreeMap().containsKey(key)) {
                return ("Нет объекта с выбранным ключом");
            } else {
                try {
                    if (worker.compareTo(getCollectionManager().getTreeMap().get(key)) < 0) {
                        getCollectionManager().getTreeMap().put(key, worker);
                        return ("Объект успешно изменён");
                    } else {
                        return ("Объект не был изменён");
                    }
                } catch (NoSuchElementException e) {
                    return ("Не удалось изменить объект");
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

    /**
     * Конструктор задает менеджер для работы с коллекцией и менеджер для работы с командами.
     *
     * @param collectionManager менеджер для работы с коллекцией
     * @param commandManager    менеджер для работы с командами
     */
    public ReplaceIfLoweCommand(CollectionManager collectionManager, CommandManager commandManager) {
        setCollectionManager(collectionManager);
        setCommandManager(commandManager);
    }
}
