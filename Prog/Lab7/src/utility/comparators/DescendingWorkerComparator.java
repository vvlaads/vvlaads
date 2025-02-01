package utility.comparators;

import utility.worker.Worker;

import java.util.Comparator;

/**
 * Класс для сортировки работников в обратном порядке.
 */
public class DescendingWorkerComparator implements Comparator<Worker> {
    /**
     * Сравнивает работников по критериям и возвращает целое число:
     * <ul>
     * <li>положительное, если вызывающий объект больше объекта, переданного в качестве параметра;</li>
     * <li>отрицательное, если вызывающий объект меньше объекта, переданного в качестве параметра;</li>
     * <li>0, если объекты равны.</li>
     * </ul>
     *
     * @param worker1 работник, с которым сравниваем
     * @param worker2 работник, с которым хотим сравнить текущего работника
     * @return <ul>
     *      <li>положительное целое число, если вызывающий объект больше объекта, переданного в качестве параметра;</li>
     *      <li>отрицательное целое число, если вызывающий объект меньше объекта, переданного в качестве параметра;</li>
     *      <li>0, если объекты равны.</li>
     *      </ul>
     */
    @Override
    public int compare(Worker worker1, Worker worker2) {
        return -worker1.compareTo(worker2);
    }
}
