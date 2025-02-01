package utility.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import utility.parser.JacksonParser;

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
     * Менеджер для работы с коллекцией.
     */
    private CollectionManager collectionManager;

    /**
     * Возвращает путь к файлу.
     *
     * @return путь к файлу
     */
    public String getFilepath() {
        return filePath;
    }

    /**
     * Устанавливает путь к файлу.
     *
     * @param filePath путь к файлу
     */
    public void setFilepath(String filePath) {
        if (filePath != null) {
            FileManager.filePath = filePath;
        } else {
            System.out.println("Ошибка! В переменной окружения \"FILEPATH\" отсутствует имя файла");
            System.exit(1);
        }
    }

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
     * Записывает строку в файл.
     *
     * @param string строка для записи
     * @throws IOException ошибка записи в файл
     */
    public void write(String string) throws IOException {
        File file = new File(getFilepath());
        if (!file.exists()) {
            throw new IOException();
        }
        if (file.canWrite()) {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(string);
            fileWriter.close();
        } else {
            System.out.println("Не удалось записать в файл");
            throw new IOException();
        }
    }

    /**
     * Чтение файла формата JSON и добавление JSON-объектов в коллекцию.
     *
     * @throws FileNotFoundException   ошибка обнаружения файла для чтения
     * @throws JsonProcessingException ошибка десериализации файла
     */
    public void read() throws FileNotFoundException, JsonProcessingException {
        File file = new File(getFilepath());
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        if (!file.canRead() || !file.canWrite() || !file.canExecute()) {
            System.out.println("Внимание! Права доступа к файлу:");
            System.out.println("read: " + file.canRead());
            System.out.println("write: " + file.canWrite());
            System.out.println("execute: " + file.canExecute());
        }
        if (file.canRead()) {
            if (file.length() > 0) {
                String json = "";
                Scanner scanner = new Scanner(file);
                while (scanner.hasNext()) {
                    json = json + scanner.next();
                }
                scanner.close();
                JacksonParser jacksonParser = new JacksonParser();
                getCollectionManager().putAll(jacksonParser.parseFromJson(json));
            }
        } else {
            System.out.println("Не удалось прочитать файл");
            throw new FileNotFoundException();
        }
    }

    /**
     * Конструктор задает менеджер для работы с коллекцией.
     *
     * @param collectionManager менеджер для работы с коллекцией
     */
    public FileManager(CollectionManager collectionManager) {
        setCollectionManager(collectionManager);
    }
}
