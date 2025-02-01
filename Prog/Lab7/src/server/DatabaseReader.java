package server;

import utility.database.DatabaseConnection;
import utility.manager.CollectionManager;
import utility.worker.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.TreeMap;

/**
 * Класс для считывания информации из Базы данных.
 */
public class DatabaseReader {
    /**
     * Менеджер для работы с коллекцией.
     */
    private CollectionManager collectionManager;
    /**
     * Подключение к базе данных.
     */
    private DatabaseConnection databaseConnection;

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
     * Возвращает подключение к базе данных.
     *
     * @return подключение к базе данных
     */
    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }

    /**
     * Устанавливает подключение к базе данных.
     *
     * @param databaseConnection подключение к базе данных
     */
    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    /**
     * Конструктор задает подключение к базе данных и менеджер для работы с коллекцией.
     *
     * @param databaseConnection подключение к базе данных
     * @param collectionManager  менеджер для работы с коллекцией
     */
    public DatabaseReader(DatabaseConnection databaseConnection, CollectionManager collectionManager) {
        setDatabaseConnection(databaseConnection);
        setCollectionManager(collectionManager);
    }

    /**
     * Метод для чтения информации из Базы данных и записи в коллекцию.
     * @throws SQLException ошибка запроса в базе данных
     */
    public void read() throws SQLException {
        TreeMap<Integer, Worker> workers = new TreeMap<>();
        int number = 0;
        Statement statement = getDatabaseConnection().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM worker;");
        while (resultSet.next()) {
            number += 1;
            Long id = resultSet.getLong(1);
            String name = resultSet.getString("name");
            long coordinates_id = resultSet.getLong("coordinates_id");
            Timestamp timestamp = resultSet.getTimestamp("creationDate");
            LocalDateTime creationDate = timestamp.toLocalDateTime();
            Long salary = resultSet.getLong("salary");
            if (salary == 0) {
                salary = null;
            }
            String position = resultSet.getString("position");
            String status = resultSet.getString("status");
            long organization_id = resultSet.getLong("organization_id");
            Statement statement1 = getDatabaseConnection().getConnection().createStatement();
            ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM COORDINATES WHERE id = " + coordinates_id + ";");
            resultSet1.next();
            long x = resultSet1.getLong("x");
            Integer y = resultSet1.getInt("y");
            statement1.close();
            Coordinates coordinates = new Coordinates(x, y);

            Statement statement2 = getDatabaseConnection().getConnection().createStatement();
            ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM ORGANIZATION WHERE id = " + organization_id + ";");
            resultSet2.next();
            String fullName = resultSet2.getString("fullName");
            Integer employeesCount = resultSet2.getInt("employeesCount");
            String type = resultSet2.getString("type");
            long address_id = resultSet2.getLong("address_id");
            statement2.close();
            Organization organization = new Organization(fullName, employeesCount, OrganizationType.valueOf(type), null);
            if (address_id != 0) {
                Statement statement3 = getDatabaseConnection().getConnection().createStatement();
                ResultSet resultSet3 = statement3.executeQuery("SELECT * FROM ADDRESS WHERE ID = " + address_id + ";");
                resultSet3.next();
                String street = resultSet3.getString("Street");
                Address address = new Address(street);
                organization.setPostalAddress(address);
                statement3.close();
            }
            Worker worker;
            if (status != null) {
                worker = new Worker(id, name, coordinates, creationDate, salary, Position.valueOf(position), Status.valueOf(status), organization);
            } else {
                worker = new Worker(id, name, coordinates, creationDate, salary, Position.valueOf(position), null, organization);
            }
            workers.put(number, worker);
        }
        statement.close();
        getCollectionManager().putAll(workers);
    }
}
