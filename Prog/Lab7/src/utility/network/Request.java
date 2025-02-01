package utility.network;

import utility.command.Argument;
import utility.command.Command;
import utility.network.ClientData;
import utility.worker.Worker;

import java.io.Serial;
import java.io.Serializable;

/**
 * Класс для составления запроса.
 */
public class Request implements Serializable {
    /**
     * Поле для сериализация.
     */
    @Serial
    private static final long serialVersionUID = 777L;
    /**
     * Информация о клиенте.
     */
    private ClientData clientData;
    /**
     * Команда для выполнения.
     */
    private Command command;
    /**
     * Аргумент для команды.
     */
    private Argument argument;
    /**
     * Работник, необходимый для работы некоторых команд.
     */
    private Worker worker;

    /**
     * Возвращает информацию о клиенте.
     *
     * @return информация о клиенте
     */
    public ClientData getClientData() {
        return clientData;
    }

    /**
     * Устанавливает информацию о клиенте.
     *
     * @param clientData информация о клиенте
     */
    public void setClientData(ClientData clientData) {
        this.clientData = clientData;
    }

    /**
     * Возвращает команду для выполнения.
     *
     * @return команду для выполнения
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Устанавливает команду для выполнения.
     *
     * @param command команда для выполнения
     */
    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * Возвращает аргумент команды.
     *
     * @return аргумент команды
     */
    public Argument getArgument() {
        return argument;
    }

    /**
     * Устанавливает аргумент для команды.
     *
     * @param argument аргумент для команды
     */
    public void setArgument(Argument argument) {
        this.argument = argument;
    }

    /**
     * Возвращает работника, необходимого для работы некоторых команд.
     *
     * @return работника, необходимого для работы некоторых команд
     */
    public Worker getWorker() {
        return worker;
    }

    /**
     * Устанавливает работника, необходимого для работы некоторых команд.
     *
     * @param worker работник, необходимый для работы некоторых команд
     */
    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    /**
     * Пустой конструктор.
     */
    public Request() {
        setCommand(null);
        setArgument(null);
        setWorker(new Worker());
        setClientData(null);
    }

    /**
     * Возвращает запрос в строковом представлении.
     *
     * @return запрос в строковом представлении
     */
    @Override
    public String toString() {
        return "Request{" +
                "command=" + command +
                ", argument=" + argument +
                ", worker=" + worker +
                '}';
    }
}
