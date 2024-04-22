package manager;

import java.io.IOException;
import java.util.*;

import parser.JacksonParser;
import worker.Worker;
import worker.Status;

/**
 * Класс для взаимодействия с коллекцией TreeMap, содержащей работников с целочисленными ключами.
 */
public class CollectionManager {
    /**
     * TreeMap, хранящая всех работников с целочисленными ключами.
     */
    private static final TreeMap<Integer, Worker> workers = new TreeMap<>();
    /**
     * Дата создания коллекции.
     */
    private static final Date dateCreated = new Date();

    /**
     * Возвращает дату создания коллекции.
     *
     * @return дата создания коллекции
     */
    public static Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Возвращает коллекцию TreeMap работников с целочисленными ключами.
     *
     * @return коллекция TreeMap работников с целочисленными ключами
     */
    public static TreeMap<Integer, Worker> getTreeMap() {
        return workers;
    }

    /**
     * Вывод в командную строку работников в строковом представлении.
     */
    public static void getWorkers() {
        for (Map.Entry<Integer, Worker> entry : workers.entrySet()) {
            Worker worker = entry.getValue();
            System.out.println(worker);
        }
    }

    /**
     * Вывод в командную строку работников в строковом представлении в обратном порядке.
     */
    public static void getDescendingWorkers() {
        for (Map.Entry<Integer, Worker> entry : workers.descendingMap().entrySet()) {
            Worker worker = entry.getValue();
            System.out.println(worker);
        }
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
    public static boolean hasKey(Integer key) {
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
    public static boolean hasID(long id) {
        for (Map.Entry<Integer, Worker> entry : workers.entrySet()) {
            Worker worker = entry.getValue();
            if (worker.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Обновляет данные о работнике по переданному ID работника.
     *
     * @param id        ID работника
     * @param newWorker работник с обновленными данными.
     */
    public static void updateWorker(long id, Worker newWorker) {
        for (Map.Entry<Integer, Worker> entry : workers.entrySet()) {
            Integer key = entry.getKey();
            Worker worker = entry.getValue();
            if (worker.getId() == id) {
                workers.put(key, newWorker);
                break;
            }
        }
    }

    /**
     * Сохраняет коллекцию TreeMap, содержащую работников с целочисленными ключами в файл.
     */
    public static void save() {
        try {
            FileManager.write(JacksonParser.parseToJson(getTreeMap()));
            System.out.println("Сохранено");
        } catch (IOException e) {
            System.out.println("Ошибка! Нельзя сохранить в файл");
        }
    }

    /**
     * Удаляет работников из коллекции, превышающих переданного работника.
     *
     * @param newWorker работник, с которым сравниваем
     */
    public static void removeGreater(Worker newWorker) {
        List<Integer> keys = new ArrayList<>();
        for (Map.Entry<Integer, Worker> entry : workers.entrySet()) {
            Integer key = entry.getKey();
            Worker worker = entry.getValue();
            if (worker.compareTo(newWorker) > 0) {
                keys.add(key);
            }
        }
        for (Integer key : keys) {
            workers.remove(key);
        }
    }

    /**
     * Удаляет из коллекции все элементы, ключ которых меньше, чем переданный ключ.
     *
     * @param key ключ
     */
    public static void removeLowerKey(Integer key) {
        Integer lowerKey = workers.lowerKey(key);
        while (lowerKey != null) {
            workers.remove(lowerKey);
            lowerKey = workers.lowerKey(key);
        }
    }

    /**
     * Возвращает сумму зарплат всех работников.
     *
     * @return сумма зарплат всех работников
     */
    public static Long sumOfSalary() {
        Long result = 0L;
        for (Map.Entry<Integer, Worker> entry : workers.entrySet()) {
            Worker worker = entry.getValue();
            if (worker.getSalary() != null) {
                result += worker.getSalary();
            }
        }
        return result;
    }

    /**
     * Возвращает работника из коллекции с минимальным статусом.
     *
     * @return работник с минимальным статусом
     */
    public static Worker minStatus() {
        Status status = Status.REGULAR;
        Worker newWorker = null;
        for (Map.Entry<Integer, Worker> entry : workers.entrySet()) {
            Worker worker = entry.getValue();
            if (worker.getStatus() != null) {
                if (worker.getStatus().compareTo(status) < 0) {
                    status = worker.getStatus();
                    newWorker = worker;
                }
            }
        }
        return newWorker;
    }
}