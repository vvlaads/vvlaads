package command;

import manager.CommandManager;

/**
 * Команда для вывода списка команд с их описанием.
 */
public class HelpCommand implements Command {
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
        return "help";
    }

    /**
     * Вывод списка команд с их описанием.
     *
     * @param args аргументы команды
     */
    public void execute(String args) {
        System.out.println();
        for (Command command : CommandManager.getCommands()) {
            System.out.println(command.descr());
        }
        System.out.println();
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "help: вывести справку по доступным командам";
    }
}
