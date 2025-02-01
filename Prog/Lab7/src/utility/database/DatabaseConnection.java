package utility.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс для подключения к Базе данных.
 */
public class DatabaseConnection {
    /**
     * URL Базы данных.
     */
    private String url;
    /**
     * Имя пользователя для подключения к Базе данных.
     */
    private String username;
    /**
     * Пароль пользователя для подключения к Базе данных.
     */
    private String password;
    /**
     * Соединение с базой данных.
     */
    private Connection connection;

    /**
     * Возвращает URL Базы данных.
     *
     * @return URL Базы данных
     */
    public String getUrl() {
        return url;
    }

    /**
     * Устанавливает URL Базы данных.
     *
     * @param url URL Базы данных
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Возвращает имя пользователя для подключения к Базе данных.
     *
     * @return имя пользователя для подключения к Базе данных
     */
    public String getUsername() {
        return username;
    }

    /**
     * Устанавливает имя пользователя для подключения к Базе данных.
     *
     * @param username имя пользователя для подключения к Базе данных
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Возвращает пароль пользователя для подключения к Базе данных.
     *
     * @return пароль пользователя для подключения к Базе данных
     */
    public String getPassword() {
        return password;
    }

    /**
     * Устанавливает пароль пользователя для подключения к Базе данных.
     *
     * @param password пароль пользователя для подключения к Базе данных
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Возвращает соединение с базой данных.
     *
     * @return соединение с базой данных
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Устанавливает соединение с базой данных.
     *
     * @param connection соединение с базой данных
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Конструктор задает URL, имя пользователя и пароль для подключения к Базе данных.
     *
     * @param url      URL Базы данных
     * @param username имя пользователя для подключения к Базе данных
     * @param password пароль пользователя для подключения к Базе данных
     */
    public DatabaseConnection(String url, String username, String password) {
        setUrl(url);
        setUsername(username);
        setPassword(password);
    }

    /**
     * Метод для создания соединения с Базой данных.
     *
     * @throws SQLException           ошибка запроса в базе данных
     * @throws ClassNotFoundException ошибка нахождения класса
     */
    public void connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        setConnection(DriverManager.getConnection(getUrl(), getUsername(), getPassword()));
    }
}