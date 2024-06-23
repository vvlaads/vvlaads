package utility.manager;

import utility.worker.Worker;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Класс для взаимодействия с коллекцией TreeMap, содержащей работников с целочисленными ключами.
 */
public class CollectionManager implements Serializable {
    /**
     * Поле для сериализации.
     */
    @Serial
    private static final long serialVersionUID = 2000L;
    /**
     * TreeMap, хранящая всех работников с целочисленными ключами.
     */
    private final TreeMap<Integer, Worker> workers = new TreeMap<>();
    /**
     * Дата создания коллекции.
     */
    private final Date dateCreated = new Date();

    /**
     * Возвращает дату создания коллекции.
     *
     * @return дата создания коллекции
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Возвращает коллекцию TreeMap работников с целочисленными ключами.
     *
     * @return коллекция TreeMap работников с целочисленными ключами
     */
    public TreeMap<Integer, Worker> getTreeMap() {
        return workers;
    }

    /**
     * Возвращает
     * <ul>
     *  <li>true, если в коллекции есть элемент с переданным ключом;</li>
     *  <li>false, если в коллекции отсутствует элемент с переданным ключом.</li>
     * </ul>
     *
     * @param key ключ
     * @return <ul>
     *  <li>true, если в коллекции есть элемент с переданным ключом;</li>
     *  <li>false, если в коллекции отсутствует элемент с переданным ключом.</li>
     * </ul>
     */
    public boolean hasKey(Integer key) {
        for (Map.Entry<Integer, Worker> entry : workers.entrySet()) {
            if (key.equals(entry.getKey())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает
     * <ul>
     *  <li>true, если в коллекции есть элемент с переданным ID работника;</li>
     *  <li>false, если в коллекции отсутствует элемент с переданным ID работника.</li>
     * </ul>
     *
     * @param id ID работника
     * @return <ul>
     *  <li>true, если в коллекции есть элемент с переданным ID работника;</li>
     *  <li>false, если в коллекции отсутствует элемент с переданным ID работника.</li>
     * </ul>
     */
    public boolean hasID(long id) {
        for (Map.Entry<Integer, Worker> entry : workers.entrySet()) {
            Worker worker = entry.getValue();
            if (worker.getId() == id) {
                return true;
            }
        }
        return false;
    }
}