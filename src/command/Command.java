package command;

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
     * @param args аргументы команды
     */
    void execute(String args);

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    String descr();
}