package lab.monitoring;

/**
 * Интерфейс MBean-а вычисляющего площадь получившейся фигуры
 */
public interface AreaCalculatorMBean {
    /**
     * Считает площадь фигуры
     *
     * @param r радиус
     * @return площадь
     */
    double calculateArea(double r);

    /**
     * Возвращает площадь последней фигуры
     *
     * @return площадь
     */
    double getLastCalculatedArea();
}
