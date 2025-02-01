package utility.manager;

import utility.worker.Worker;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
     * Блокировщик, для синхронизированного доступа к коллекции.
     */
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
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
     * Положить объект в коллекцию.
     *
     * @param key    ключ
     * @param worker работник
     */
    public void put(Integer key, Worker worker) {
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        workers.put(key, worker);
        writeLock.unlock();
    }

    /**
     * Положить TreeMap в коллекцию.
     *
     * @param treeMap TreeMap
     */
    public void putAll(TreeMap<Integer, Worker> treeMap) {
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        workers.putAll(treeMap);
        writeLock.unlock();
    }

    /**
     * Удалить объект из коллекции.
     *
     * @param key ключ объекта
     */
    public void remove(Integer key) {
        Lock readLock = lock.readLock();
        Lock writeLock = lock.writeLock();
        readLock.lock();
        writeLock.lock();
        workers.remove(key);
        readLock.unlock();
        writeLock.unlock();
    }

    /**
     * Получить объект по ключу.
     *
     * @param key ключ объекта
     * @return Работник с выбранным ключом
     */
    public Worker get(Integer key) {
        Lock readLock = lock.readLock();
        readLock.lock();
        Worker worker = workers.get(key);
        readLock.unlock();
        return worker;
    }

    /**
     * Получить EntrySet коллекции.
     *
     * @return entrySet коллекции
     */
    public Set<Map.Entry<Integer, Worker>> getEntrySet() {
        Lock readLock = lock.readLock();
        readLock.lock();
        Set<Map.Entry<Integer, Worker>> workersSet = workers.entrySet();
        readLock.unlock();
        return workersSet;
    }

    /**
     * Получить Values коллекции.
     *
     * @return values коллекции
     */
    public Collection<Worker> getValues() {
        Lock readLock = lock.readLock();
        readLock.lock();
        Collection<Worker> workerCollection = workers.values();
        readLock.unlock();
        return workerCollection;
    }

    /**
     * Получить KeySet коллекции.
     *
     * @return keySet коллекции
     */
    public Set<Integer> getKeySet() {
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        Set<Integer> keySet = workers.keySet();
        writeLock.unlock();
        return keySet;
    }

    /**
     * Возвращает размер коллекции.
     *
     * @return размер коллекции
     */
    public int size() {
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        int length = workers.size();
        writeLock.unlock();
        return length;
    }

    /**
     * Возвращает класс коллекции.
     *
     * @return класс коллекции
     */
    public Class getCollectionClass() {
        return workers.getClass();
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