import manager.*;

import java.io.FileNotFoundException;
import java.util.Scanner;

import io.github.cdimascio.dotenv.Dotenv;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Класс для запуска консольного приложения.
 */
public class Main {
    /**
     * Запускает консольное приложение.
     *
     * @param args значения, передаваемые в командной строке
     */
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        Scanner sc = new Scanner(System.in);
        CollectionManager collectionManager = new CollectionManager();
        CommandManager commandManager = new CommandManager(collectionManager, sc);
        commandManager.addAllCommands();
        FileManager fileManager = new FileManager(collectionManager);
        fileManager.setFilepath(dotenv.get("FILEPATH"));
        try {
            fileManager.read();
        } catch (FileNotFoundException e) {
            System.out.println("Не найден файл. Укажите путь к файлу в переменной окружения \"FILEPATH\"");
            System.exit(1);
        } catch (JsonProcessingException e) {
            System.out.println("Ошибка! Невозможно преобразовать JSON файл в коллекцию");
            System.exit(1);
        }
        System.out.println("Введите команду: ");
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (!line.isBlank()) {
                commandManager.execute(line);
            }
        }
        sc.close();
    }
}
