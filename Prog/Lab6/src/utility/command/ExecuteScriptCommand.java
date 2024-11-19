package utility.command;

import utility.worker.Worker;
import utility.manager.CollectionManager;
import utility.manager.CommandManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Команда для выполнения скрипта.
 */
public class ExecuteScriptCommand implements Command, Serializable {
    /**
     * Поле для сериализация.
     */
    @Serial
    private static final long serialVersionUID = 103L;
    /**
     * Менеджер для работы с коллекцией.
     */
    private CollectionManager collectionManager;

    /**
     * Количество вызовов команд этого типа.
     */
    private static int countCalls = 0;

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
        return "execute_script";
    }

    /**
     * Выполнение скрипта из файла.
     *
     * @param argument путь к файлу, содержащий скрипт
     * @return сообщение о выполнении команды
     */
    public String execute(Argument argument, Worker worker) {
        countCalls += 1;
        if (countCalls < 50) {
            try {
                File file = new File(argument.getName());
                if (!file.exists()) {
                    throw new FileNotFoundException();
                } else {
                    if (file.canRead()) {
                        String message = "";
                        Scanner scanner = new Scanner(file);
                        CommandManager commandManager = new CommandManager(getCollectionManager());
                        commandManager.addAllCommands();
                        while (scanner.hasNext()) {
                            String line = scanner.nextLine();
                            message += commandManager.execute(line, scanner);
                        }
                        scanner.close();
                        countCalls -= 1;
                        return message;
                    } else {
                        countCalls -= 1;
                        return ("Отсутствуют права на чтение файла");
                    }
                }
            } catch (FileNotFoundException e) {
                countCalls -= 1;
                return ("Ошибка! Файл не найден");
            }
        } else {
            return ("\nПревышено количество одновременно исполняемых скриптов");
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "execute_script file_name: считать и исполнить скрипт из указанного файла.";
    }

    /**
     * Конструктор задает менеджер для работы с коллекцией.
     *
     * @param collectionManager менеджер для работы с коллекцией
     */
    public ExecuteScriptCommand(CollectionManager collectionManager) {
        setCollectionManager(collectionManager);
    }

}