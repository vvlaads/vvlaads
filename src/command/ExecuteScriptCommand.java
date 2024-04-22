package command;

import manager.FileManager;

import java.io.FileNotFoundException;

/**
 * Команда для выполнения скрипта.
 */
public class ExecuteScriptCommand implements Command {
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
        return true;
    }

    /**
     * Возвращает название команды.
     *
     * @return название команды
     */
    public String getName() {
        return "execute_script";
    }

    /**
     * Выполнение скрипта из файла.
     *
     * @param args путь к файлу, содержащий скрипт
     */
    public void execute(String args) {
        try {
            FileManager.executeScript(args);
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка! Файл не найден");
        }

    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "execute_script file_name: считать и исполнить скрипт из указанного файла.";
    }
}
