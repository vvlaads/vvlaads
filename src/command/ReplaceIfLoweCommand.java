package command;

import calculate.Calculate;
import calculate.CreateWorker;
import manager.CollectionManager;
import manager.CommandManager;
import worker.Worker;

import java.util.NoSuchElementException;

/**
 * Команда для замены работника, если он меньше.
 */
public class ReplaceIfLoweCommand implements Command {
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
     * @param args ключ
     */
    public void execute(String args) {
        if (!Calculate.stringIsInteger(args)) {
            System.out.println("Неверное значение аргумента");
        } else {
            Integer key = Integer.parseInt(args);
            if (!getCollectionManager().getTreeMap().containsKey(key)) {
                System.out.println("Нет объекта с выбранным ключом");
            } else {
                try {
                    CreateWorker createWorker = new CreateWorker();
                    Worker worker = createWorker.createWorker(getCommandManager().getScanner());
                    if (worker.compareTo(getCollectionManager().getTreeMap().get(key)) < 0) {
                        getCollectionManager().getTreeMap().put(key, worker);
                        System.out.println("Объект успешно изменён");
                    } else {
                        System.out.println("Объект не был изменён");
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("Не удалось изменить объект");
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
