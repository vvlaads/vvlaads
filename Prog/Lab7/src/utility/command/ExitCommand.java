package utility.command;

import utility.worker.Worker;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда для выхода из программы.
 */
public class ExitCommand implements Command, Serializable {
    /**
     * Поле для сериализация.
     */
    @Serial
    private static final long serialVersionUID = 104L;
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
        return "exit";
    }

    /**
     * Остановка программы.
     *
     * @param argument аргументы команды
     */
    public String execute(Argument argument, Worker worker) {
        return ("Завершение программы");
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "exit: завершить программу (без сохранения в файл)";
    }
}
