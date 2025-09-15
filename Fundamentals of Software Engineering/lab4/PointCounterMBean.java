package lab.monitoring;

/**
 * Интерфейс MBean-а для подсчёта числа точек пользователя
 */
public interface PointCounterMBean {
    /**
     * Считает число точек у пользователя
     *
     * @param login логин пользователя
     * @return число точек
     */
    int countPointsForUser(String login);

    /**
     * Считает число точек у пользователя, попадающих в область
     *
     * @param login логин пользователя
     * @return число точек
     */
    int countPointsInAreaForUser(String login);

    /**
     * Возвращает общее число точек
     *
     * @return число точек
     */
    int getTotalPoints();

    /**
     * Возвращает общее число точек в области
     *
     * @return число точек
     */
    int getTotalPointsInArea();
}
