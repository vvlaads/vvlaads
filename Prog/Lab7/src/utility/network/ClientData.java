package utility.network;

import java.io.Serial;
import java.io.Serializable;
import java.net.SocketAddress;

/**
 * Класс для хранения информации о клиенте.
 */
public class ClientData implements Serializable {
    /**
     * Поле для сериализации.
     */
    @Serial
    private static final long serialVersionUID = 777L;
    /**
     * Логин пользователя.
     */
    private String username;
    /**
     * Пароль пользователя.
     */
    private String password;
    /**
     * Адрес клиента.
     */
    private SocketAddress socketAddress;

    /**
     * Возвращает логин пользователя.
     *
     * @return логин пользователя
     */
    public String getUsername() {
        return username;
    }

    /**
     * Устанавливает логин пользователя.
     *
     * @param username логин пользователя
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Возвращает пароль пользователя.
     *
     * @return пароль пользователя
     */
    public String getPassword() {
        return password;
    }

    /**
     * Устанавливает пароль пользователя.
     *
     * @param password пароль пользователя
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Возвращает адрес пользователя.
     *
     * @return адрес пользователя
     */
    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

    /**
     * Устанавливает адрес пользователя.
     *
     * @param socketAddress адрес пользователя
     */
    public void setSocketAddress(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    /**
     * Конструктор задает логин и пароль пользователя.
     *
     * @param username логин пользователя
     * @param password пароль пользователя
     */
    public ClientData(String username, String password) {
        setUsername(username);
        setPassword(password);
    }
}
