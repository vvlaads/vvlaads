package utility.command;

import utility.worker.Worker;

/**
 * Интерфейс, реализуемый всеми командами консольного приложения.
 */
public interface Command {
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
    boolean hasArguments();

    /**
     * Возвращает название команды.
     *
     * @return название команды
     */
    String getName();

    /**
     * Выполнение команды.
     *
     * @param argument аргументы команды
     * @param worker   работник, необходимый для выполнения некоторых команд
     * @return сообщение о выполнении команды
     */
    String execute(Argument argument, Worker worker);

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    String descr();
}