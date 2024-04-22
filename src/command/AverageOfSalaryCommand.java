package command;

import manager.CollectionManager;

/**
 * Команда для подсчёта средней зарплаты.
 */
public class AverageOfSalaryCommand implements Command {
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
        return "average_of_salary";
    }

    /**
     * Вывод средней зарплаты рабочих.
     *
     * @param args аргументы команды
     */
    public void execute(String args) {
        int count = CollectionManager.getTreeMap().size();
        Long sumOfSalary = CollectionManager.sumOfSalary();
        if (count == 0) {
            System.out.println("Невозможно посчитать среднюю зарплату, т.к. отсутствуют работники в коллекции");
        } else {
            double result = (double) sumOfSalary / count;
            System.out.println("Средняя зарплата: " + result);
        }

    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String descr() {
        return "average_of_salary : вывести среднее значение поля salary для всех элементов коллекции";
    }
}
