package server;

import com.fasterxml.jackson.core.JsonProcessingException;
import utility.manager.CollectionManager;
import utility.manager.CommandManager;
import utility.manager.FileManager;

import java.io.FileNotFoundException;

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
        String path = "Tests/Test.json";//System.getenv("FILEPATH");
        CollectionManager collectionManager = new CollectionManager();
        CommandManager commandManager = new CommandManager(collectionManager);
        commandManager.addAllCommands();
        FileManager fileManager = new FileManager(collectionManager);
        fileManager.setFilepath(path);
        try {
            fileManager.read();
        } catch (FileNotFoundException e) {
            System.out.println("Не найден файл. Укажите путь к файлу в переменной окружения \"FILEPATH\"");
            System.exit(1);
        } catch (JsonProcessingException e) {
            System.out.println("Ошибка! Невозможно преобразовать JSON файл в коллекцию");
            System.exit(1);
        }

        Server server = new Server(commandManager, 4444);
        server.run();
    }
}
