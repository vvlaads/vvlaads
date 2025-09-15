package lab.monitoring;

import jakarta.ejb.Local;
import lab.database.Point;

/**
 * Интерфейс MBean-а для статистики точек пользователя
 */
@Local
public interface PointCounterService {
    /**
     * Метод для вызова при добавлении точки пользователем
     *
     * @param point точка для проверки
     * @param login логин пользователя
     */
    void pointAdded(Point point, String login);
}
