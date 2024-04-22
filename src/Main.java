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
        FileManager.setFilepath(dotenv.get("FILEPATH"));
        try {
            FileManager.read();
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка! Файл не найден. Укажите путь к файлу в переменной окружения \"FILEPATH\"");
            System.exit(1);
        } catch (JsonProcessingException e) {
            System.out.println("Ошибка! Невозможно преобразовать JSON файл в коллекцию");
            System.exit(1);
        }
        CommandManager.addAllCommands();

        Scanner sc = new Scanner(System.in);
        System.out.println("Введите команду: ");
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            CommandManager.execute(line);
        }
        sc.close();
    }
}
