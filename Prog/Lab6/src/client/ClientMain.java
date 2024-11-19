package client;

import utility.manager.CollectionManager;
import utility.manager.CommandManager;

/**
 * Класс для запуска клиента.
 */
public class ClientMain {
    /**
     * Точка запуска клиента.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        CollectionManager collectionManager = new CollectionManager();
        CommandManager commandManager = new CommandManager(collectionManager);
        commandManager.addAllCommands();
        Client client = new Client(commandManager, 4444);
        client.run();
    }
}
