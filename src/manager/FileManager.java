package manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import parser.JacksonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Класс для взаимодействия с файлами.
 */
public class FileManager {
    /**
     * Путь к файлу.
     */
    private static String filePath;

    /**
     * Возвращает путь к файлу.
     *
     * @return путь к файлу
     */
    public static String getFilepath() {
        return filePath;
    }

    /**
     * Устанавливает путь к файлу.
     *
     * @param filePath путь к файлу
     */
    public static void setFilepath(String filePath) {
        if (filePath != null) {
            FileManager.filePath = filePath;
        } else {
            System.out.println("Ошибка! В переменной окружения \"FILEPATH\" отсутствует имя файла");
            System.exit(1);
        }
    }

    /**
     * Записывает строку в файл.
     *
     * @param string строка для записи
     * @throws IOException ошибка записи в файл
     */
    public static void write(String string) throws IOException {
        File file = new File(getFilepath());
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(string);
        fileWriter.close();
    }

    /**
     * Чтение файла формата JSON и добавление JSON-объектов в коллекцию.
     *
     * @throws FileNotFoundException   ошибка обнаружения файла для чтения
     * @throws JsonProcessingException ошибка десериализации файла
     */
    public static void read() throws FileNotFoundException, JsonProcessingException {
        File file = new File(getFilepath());
        String json = "";
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            json = json + scanner.next();
        }
        scanner.close();
        CollectionManager.getTreeMap().putAll(JacksonParser.parseFromJson(json));
    }

    /**
     * Выполняет скрипт из файла
     *
     * @param filePath путь к файлу
     * @throws FileNotFoundException ошибка обнаружения файла для чтения
     */
    public static void executeScript(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            CommandManager.execute(line);
        }
        scanner.close();
    }
}
