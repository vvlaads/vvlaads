package command;

import manager.CommandManager;

/**
 * Команда для вывода списка команд с их описанием.
 */
public class HelpCommand implements Command {
    /**
     * Менеджер для работы с командами.
     */
    private CommandManager commandManager;

    /**
     * Возвращает менеджер для работы с командами.
     *
     * @return менеджер для работы с командами
     */
    public CommandManager getCommandManager() {
        return commandManager;
    }

    /**
     * Устанавливает менеджер для работы с командами.
     *
     * @param commandManager менеджер для работы с командами
     */
    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

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
        for (Command command : getCommandManager().getCommands()) {
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

    /**
     * Конструктор задает менеджер для работы с командами.
     *
     * @param commandManager менеджер для работы с командами
     */
    public HelpCommand(CommandManager commandManager) {
        setCommandManager(commandManager);
    }
}
