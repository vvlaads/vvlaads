package client;

import utility.manager.CollectionManager;
import utility.manager.CommandManager;
import utility.network.ClientData;

import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя пользователя: ");
        String username = scanner.next();
        System.out.println("Введите пароль: ");
        String password = scanner.next();
        ClientData clientData = new ClientData(username, password);
        CollectionManager collectionManager = new CollectionManager();
        CommandManager commandManager = new CommandManager(collectionManager, null);
        commandManager.addAllCommands();
        Client client = new Client(commandManager, 4444, clientData);
        client.run();
    }
}
