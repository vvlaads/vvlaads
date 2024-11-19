package command;

import manager.CollectionManager;
import manager.FileManager;
import parser.JacksonParser;

import java.io.IOException;

/**
 * Команда для сохранения коллекции в файл.
 */
public class SaveCommand implements Command {
    /**
     * Менеджер для работы с коллекцией.
     */
    private CollectionManager collectionManager;

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
        return false;
    }

    /**
     * Возвращает название команды.
     *
     * @return название команды
     */
    public String getName() {
        return "save";
    }

    /**
     * Сохранение текущей коллекции работников в файл.
     *
     * @param args аргументы команды
     */
    public void execute(String args) {
        FileManager fileManager = new FileManager(getCollectionManager());
        try {
            JacksonParser jacksonParser = new JacksonParser();
            fileManager.write(jacksonParser.parseToJson(getCollectionManager().getTreeMap()));
            System.out.println("Сохранено");
        } catch (IOException e) {
            System.out.println("Ошибка! Нельзя сохранить в файл");
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "save: сохранить коллекцию в файл";
    }

    /**
     * Конструктор задает менеджер для работы с коллекцией.
     *
     * @param collectionManager менеджер для работы с коллекцией
     */
    public SaveCommand(CollectionManager collectionManager) {
        setCollectionManager(collectionManager);
    }
}
