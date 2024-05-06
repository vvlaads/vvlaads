package command;

import manager.CollectionManager;
import manager.CommandManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Команда для выполнения скрипта.
 */
public class ExecuteScriptCommand implements Command {
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
     * @param args путь к файлу, содержащий скрипт
     */
    public void execute(String args) {
        countCalls += 1;
        if (countCalls < 50) {
            try {
                File file = new File(args);
                if (!file.exists()) {
                    throw new FileNotFoundException();
                } else {
                    if (file.canRead()) {
                        Scanner scanner = new Scanner(file);
                        CommandManager commandManager = new CommandManager(getCollectionManager(), scanner);
                        commandManager.addAllCommands();
                        while (scanner.hasNext()) {
                            String line = scanner.nextLine();
                            commandManager.execute(line);
                        }
                        scanner.close();
                    } else {
                        System.out.println("Отсутствуют права на чтение файла");
                    }
                    countCalls -= 1;
                }
            } catch (FileNotFoundException e) {
                System.out.println("Ошибка! Файл не найден");
                countCalls -= 1;
            }
        } else {
            System.out.println("Превышено количество одновременно исполняемых скриптов");
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