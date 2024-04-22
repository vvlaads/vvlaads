package command;

/**
 * Команда для выхода из программы.
 */
public class ExitCommand implements Command {
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
     * @param args аргументы команды
     */
    public void execute(String args) {
        System.out.println("Завершение программы");
        System.exit(0);
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
