package server;

import utility.database.DatabaseConnection;
import utility.database.DatabaseManager;
import utility.manager.CollectionManager;
import utility.manager.CommandManager;

import java.sql.SQLException;

/**
 * Класс для запуска сервера.
 */
public class ServerMain {
    /**
     * Точка запуска серверного приложения.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        String url = "jdbc:postgresql://pg:5432/studs";
        String username = System.getenv("DB_NAME");
        if (username == null) {
            System.out.println("Не указан логин в переменной окружения DB_NAME");
            System.exit(1);
        }
        String password = System.getenv("DB_PASS");
        if (password == null) {
            System.out.println("Не указан логин в переменной окружения DB_PASS");
            System.exit(1);
        }
        DatabaseConnection databaseConnection = new DatabaseConnection(url, username, password);
        DatabaseManager databaseManager = new DatabaseManager(databaseConnection);
        CollectionManager collectionManager = new CollectionManager();
        CommandManager commandManager = new CommandManager(collectionManager, databaseManager);
        commandManager.addAllCommands();
        DatabaseReader databaseReader = new DatabaseReader(databaseConnection, collectionManager);
        try {
            databaseConnection.connect();
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка драйвера jdbc");
            System.exit(1);
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к Базе данных");
            System.exit(1);
        }
        try {
            databaseReader.read();
        } catch (SQLException e) {
            System.out.println("Не удалось прочитать данные из Базы Данных");
        }

        Server server = new Server(commandManager, databaseManager, 4444);
        try {
            server.startMultiThreading();
        } catch (Exception e) {
            server.stop();
        }
    }
}
