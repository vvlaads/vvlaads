package utility.database;

import utility.network.ClientData;
import utility.calculate.HashPassword;
import utility.worker.Address;
import utility.worker.Coordinates;
import utility.worker.Organization;
import utility.worker.Worker;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

/**
 * Класс для взаимодействия с Базой данных.
 */
public class DatabaseManager {
    /**
     * Подключение к базе данных.
     */
    private DatabaseConnection databaseConnection;

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
     * Конструктор задает подключение к базе данных.
     *
     * @param databaseConnection подключение к базе данных
     */
    public DatabaseManager(DatabaseConnection databaseConnection) {
        setDatabaseConnection(databaseConnection);
    }

    /**
     * Метод для добавления в Базу данных объекта типа Worker.
     *
     * @param worker     объект типа Worker
     * @param clientData информация о клиенте
     * @return логическое значение
     * <ul>
     * <li>true, если объект успешно добавлен</li>
     * <li>false, если не удалось добавить объект</li>
     * </ul>
     */
    public boolean insertWorker(Worker worker, ClientData clientData) {
        try {
            Long coordinates_id = insertCoordinates(worker.getCoordinates());
            Long organization_id = insertOrganization(worker.getOrganization());
            String username = clientData.getUsername();
            Timestamp timestamp = Timestamp.valueOf(worker.getCreationDate());
            String insertWorker = "INSERT INTO worker(name, coordinates_id, creationDate, salary, position, status, organization_id, username) " +
                    "VALUES(?, ?, ?, ?, cast(? as position_type), cast(? as status_type), ?, ?);";
            PreparedStatement preparedStatement = getDatabaseConnection().getConnection().prepareStatement(insertWorker);
            preparedStatement.setString(1, worker.getName());
            preparedStatement.setLong(2, coordinates_id);
            preparedStatement.setTimestamp(3, timestamp);
            if (worker.getSalary() != null) {
                preparedStatement.setLong(4, worker.getSalary());
            } else {
                preparedStatement.setNull(4, Types.BIGINT);
            }
            preparedStatement.setString(5, worker.getPosition().toString());
            if (worker.getStatus() != null) {
                preparedStatement.setString(6, worker.getStatus().toString());
            } else {
                preparedStatement.setNull(6, Types.VARCHAR);
            }
            preparedStatement.setLong(7, organization_id);
            preparedStatement.setString(8, username);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Метод для добавления в Базу данных объекта типа Coordinates.
     *
     * @param coordinates объект типа Coordinates
     * @return id добавленного объекта
     * @throws SQLException ошибка запроса в базе данных
     */
    public Long insertCoordinates(Coordinates coordinates) throws SQLException {
        String insertCoordinates = "INSERT INTO coordinates(x, y) VALUES(?, ?)";
        PreparedStatement preparedStatement = getDatabaseConnection().getConnection().prepareStatement(insertCoordinates);
        preparedStatement.setLong(1, coordinates.getX());
        preparedStatement.setInt(2, coordinates.getY());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return getLastCoordinates();
    }

    /**
     * Метод для добавления в Базу данных объекта типа Organization.
     *
     * @param organization объект типа Organization
     * @return id добавленного объекта
     * @throws SQLException ошибка запроса в базе данных
     */
    public Long insertOrganization(Organization organization) throws SQLException {
        Long address_id = null;
        if (organization.getPostalAddress() != null) {
            address_id = insertAddress(organization.getPostalAddress());
        }
        String insertOrganization = "INSERT INTO organization(fullName, employeesCount, type, address_id) " +
                "VALUES(?, ?, cast(? as organizationType_type), ?);";
        PreparedStatement preparedStatement = getDatabaseConnection().getConnection().prepareStatement(insertOrganization);
        preparedStatement.setString(1, organization.getFullName());
        preparedStatement.setInt(2, organization.getEmployeesCount());
        preparedStatement.setString(3, organization.getType().toString());
        if (address_id != null) {
            preparedStatement.setLong(4, address_id);
        } else {
            preparedStatement.setNull(4, Types.BIGINT);
        }
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return getLastOrganization();
    }

    /**
     * Метод для добавления в Базу данных объекта типа Address.
     *
     * @param address объект типа Address
     * @return id добавленного объекта
     * @throws SQLException ошибка запроса в базе данных
     */
    public Long insertAddress(Address address) throws SQLException {
        String insertAddress = "INSERT INTO address(street) VALUES(?);";
        PreparedStatement preparedStatement = getDatabaseConnection().getConnection().prepareStatement(insertAddress);
        preparedStatement.setString(1, address.getStreet());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return getLastAddress();
    }

    /**
     * Метод для изменения в Базе данных объекта типа Worker.
     *
     * @param worker     объект типа Worker
     * @param id         id объекта, который необходимо изменить
     * @param clientData информация о клиенте
     * @return логическое значение
     * <ul>
     * <li>true, если объект успешно изменен</li>
     * <li>false, если не удалось изменить объект</li>
     * </ul>
     */
    public boolean updateWorker(Worker worker, Long id, ClientData clientData) {
        try {
            String selectWorker = "SELECT * FROM worker WHERE id = ?;";
            PreparedStatement preparedStatement1 = getDatabaseConnection().getConnection().prepareStatement(selectWorker);
            preparedStatement1.setLong(1, id);
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("username");
                long coordinates_id = resultSet.getLong("coordinates_id");
                long organization_id = resultSet.getLong("organization_id");
                if (username.equals(clientData.getUsername())) {
                    if (!updateCoordinates(coordinates_id, worker.getCoordinates())) {
                        return false;
                    }
                    if (!updateOrganization(organization_id, worker.getOrganization())) {
                        return false;
                    }
                    Timestamp timestamp = Timestamp.valueOf(worker.getCreationDate());
                    String updateWorker = "UPDATE worker SET name = ?, coordinates_id = ?, creationDate = ?, salary = ?" +
                            ", position = cast(? as position_type), status = cast(? as status_type), organization_id = ? WHERE id = ?;";
                    PreparedStatement preparedStatement2 = getDatabaseConnection().getConnection().prepareStatement(updateWorker);
                    preparedStatement2.setString(1, worker.getName());
                    preparedStatement2.setLong(2, coordinates_id);
                    preparedStatement2.setTimestamp(3, timestamp);
                    if (worker.getSalary() != null) {
                        preparedStatement2.setLong(4, worker.getSalary());
                    } else {
                        preparedStatement2.setNull(4, Types.BIGINT);
                    }
                    preparedStatement2.setString(5, worker.getPosition().toString());
                    if (worker.getStatus() != null) {
                        preparedStatement2.setString(6, worker.getStatus().toString());
                    } else {
                        preparedStatement2.setNull(6, Types.VARCHAR);
                    }
                    preparedStatement2.setLong(7, organization_id);
                    preparedStatement2.setLong(8, id);
                    preparedStatement2.executeUpdate();
                    preparedStatement2.close();
                    return true;
                }
            }
            preparedStatement1.close();
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Метод для изменения в Базе данных объекта типа Coordinates.
     *
     * @param id          id объекта, который необходимо изменить
     * @param coordinates объект типа Coordinates
     * @return логическое значение
     * <ul>
     * <li>true, если объект успешно изменен</li>
     * <li>false, если не удалось изменить объект</li>
     * </ul>
     */
    public boolean updateCoordinates(Long id, Coordinates coordinates) {
        try {
            String selectCoordinates = "SELECT * FROM coordinates WHERE id = ?;";
            PreparedStatement preparedStatement1 = getDatabaseConnection().getConnection().prepareStatement(selectCoordinates);
            preparedStatement1.setLong(1, id);
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                String updateCoordinates = "UPDATE coordinates SET x = ?, y = ? WHERE id = ?;";
                PreparedStatement preparedStatement2 = getDatabaseConnection().getConnection().prepareStatement(updateCoordinates);
                preparedStatement2.setLong(1, coordinates.getX());
                preparedStatement2.setInt(2, coordinates.getY());
                preparedStatement2.setLong(3, id);
                preparedStatement2.executeUpdate();
                preparedStatement2.close();
                return true;
            }
            preparedStatement1.close();
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Метод для изменения в Базе данных объекта типа Organization.
     *
     * @param id           id объекта, который необходимо изменить
     * @param organization объект типа Organization
     * @return логическое значение
     * <ul>
     * <li>true, если объект успешно изменен</li>
     * <li>false, если не удалось изменить объект</li>
     * </ul>
     */
    public boolean updateOrganization(Long id, Organization organization) {
        try {
            String selectOrganization = "SELECT * FROM organization WHERE id = ?;";
            PreparedStatement preparedStatement1 = getDatabaseConnection().getConnection().prepareStatement(selectOrganization);
            preparedStatement1.setLong(1, id);
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                Long address_id = null;
                if (organization.getPostalAddress() != null) {
                    address_id = resultSet.getLong("address_id");
                    if (address_id == 0L) {
                        address_id = insertAddress(organization.getPostalAddress());
                    } else {
                        if (!updateAddress(address_id, organization.getPostalAddress())) {
                            return false;
                        }
                    }
                }
                String updateOrganization = "UPDATE organization SET fullName = ?, employeesCount = ?" +
                        ", type = cast(? as organizationType_type), address_id = ? WHERE id = ?;";
                PreparedStatement preparedStatement2 = getDatabaseConnection().getConnection().prepareStatement(updateOrganization);
                preparedStatement2.setString(1, organization.getFullName());
                preparedStatement2.setInt(2, organization.getEmployeesCount());
                preparedStatement2.setString(3, organization.getType().toString());
                if (address_id != null) {
                    preparedStatement2.setLong(4, address_id);
                } else {
                    preparedStatement2.setNull(4, Types.BIGINT);
                }
                preparedStatement2.setLong(5, id);
                preparedStatement2.executeUpdate();
                preparedStatement2.close();
                return true;
            }
            preparedStatement1.close();
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Метод для изменения в Базе данных объекта типа Address.
     *
     * @param id      id объекта, который необходимо изменить
     * @param address объект типа Address
     * @return логическое значение
     * <ul>
     * <li>true, если объект успешно изменен</li>
     * <li>false, если не удалось изменить объект</li>
     * </ul>
     */
    public boolean updateAddress(Long id, Address address) {
        try {
            String selectAddress = "SELECT * FROM address WHERE id = ?;";
            PreparedStatement preparedStatement1 = getDatabaseConnection().getConnection().prepareStatement(selectAddress);
            preparedStatement1.setLong(1, id);
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                String updateAddress = "UPDATE address SET street = ? WHERE id = ?;";
                PreparedStatement preparedStatement2 = getDatabaseConnection().getConnection().prepareStatement(updateAddress);
                preparedStatement2.setString(1, address.getStreet());
                preparedStatement2.setLong(2, id);
                preparedStatement2.executeUpdate();
                preparedStatement2.close();
                return true;
            }
            preparedStatement1.close();
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Метод для удаления из Базы данных объекта типа Worker.
     *
     * @param worker     объект типа Worker
     * @param clientData информация о клиенте
     * @return логическое значение
     * <ul>
     * <li>true, если объект успешно удален</li>
     * <li>false, если не удалось удалить объект</li>
     * </ul>
     */
    public boolean removeWorker(Worker worker, ClientData clientData) {
        try {
            long id = worker.getId();
            String selectWorker = "SELECT * FROM WORKER WHERE id = ?;";
            PreparedStatement preparedStatement = getDatabaseConnection().getConnection().prepareStatement(selectWorker);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("username");
                if (username.equals(clientData.getUsername())) {
                    String deleteWorker = "DELETE FROM WORKER WHERE id = ?;";
                    PreparedStatement preparedStatement1 = getDatabaseConnection().getConnection().prepareStatement(deleteWorker);
                    preparedStatement1.setLong(1, id);
                    preparedStatement1.executeUpdate();
                    preparedStatement1.close();
                    return true;
                }
            }
            preparedStatement.close();
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Метод для проверки авторизации пользователя.
     *
     * @param clientData информация о клиенте
     * @return логическое значение
     * <ul>
     * <li>true, если пользователь авторизован</li>
     * <li>false, если пользователь не авторизован</li>
     * </ul>
     * @throws SQLException             ошибка запроса в базе данных
     * @throws NoSuchAlgorithmException ошибка преобразования
     */
    public boolean authorizedUser(ClientData clientData) throws SQLException, NoSuchAlgorithmException {
        String selectUser = "SELECT * FROM users WHERE name = ?;";
        PreparedStatement preparedStatement = getDatabaseConnection().getConnection().prepareStatement(selectUser);
        preparedStatement.setString(1, clientData.getUsername());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String password = resultSet.getString("password");
            HashPassword hashPassword = new HashPassword();
            if (password.equals(hashPassword.hashPassword(clientData.getPassword()))) {
                preparedStatement.close();
                return true;
            } else {
                preparedStatement.close();
                return false;
            }
        }
        preparedStatement.close();
        return registerUser(clientData);
    }

    /**
     * Метод для регистрации пользователя.
     *
     * @param clientData информация о клиенте
     * @return логическое значение
     * <ul>
     * <li>true, если пользователь успешно зарегистрирован</li>
     * <li>false, если пользователя не удалось зарегистрировать</li>
     * </ul>
     */
    public boolean registerUser(ClientData clientData) {
        try {
            HashPassword hashPassword = new HashPassword();
            String insertUser = "INSERT INTO users(name, password) VALUES(?, ?);";
            PreparedStatement preparedStatement = getDatabaseConnection().getConnection().prepareStatement(insertUser);
            preparedStatement.setString(1, clientData.getUsername());
            preparedStatement.setString(2, hashPassword.hashPassword(clientData.getPassword()));
            preparedStatement.execute();
            preparedStatement.close();
            return true;
        } catch (SQLException | NoSuchAlgorithmException e) {
            return false;
        }
    }

    /**
     * Метод для получения id последнего объекта типа Worker.
     *
     * @return id объекта типа Worker
     */
    public Long getLastWorker() {
        try {
            Statement statement = getDatabaseConnection().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM worker;");
            long result = -1L;
            while (resultSet.next()) {
                result = resultSet.getLong("id");
            }
            statement.close();
            resultSet.close();
            return result;
        } catch (SQLException e) {
            return -1L;
        }
    }

    /**
     * Метод для получения id последнего объекта типа Coordinates.
     *
     * @return id объекта типа Coordinates
     */
    public Long getLastCoordinates() {
        try {
            Statement statement = getDatabaseConnection().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM COORDINATES;");
            long result = -1L;
            while (resultSet.next()) {
                result = resultSet.getLong(1);
            }
            statement.close();
            resultSet.close();
            return result;
        } catch (SQLException e) {
            return -1L;
        }
    }

    /**
     * Метод для получения id последнего объекта типа Address.
     *
     * @return id объекта типа Address
     */
    public Long getLastAddress() {
        try {
            Statement statement = getDatabaseConnection().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ADDRESS;");
            long result = -1L;
            while (resultSet.next()) {
                result = resultSet.getLong(1);
            }
            statement.close();
            resultSet.close();
            return result;
        } catch (SQLException e) {
            return -1L;
        }
    }

    /**
     * Метод для получения id последнего объекта типа Organization.
     *
     * @return id объекта типа Organization
     */
    public Long getLastOrganization() {
        try {
            Statement statement = getDatabaseConnection().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ORGANIZATION;");
            long result = -1L;
            while (resultSet.next()) {
                result = resultSet.getLong(1);
            }
            statement.close();
            resultSet.close();
            return result;
        } catch (SQLException e) {
            return -1L;
        }
    }
}
