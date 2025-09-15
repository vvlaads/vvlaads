package lab.monitoring;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import lab.database.Point;
import lab.database.PointManager;
import lab.database.User;
import lab.database.UserManager;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MBean для подсчёта числа точек пользователя
 */
@Startup
@Singleton
public class PointCounter implements PointCounterMBean, PointCounterService, NotificationEmitter {
    @EJB
    private UserManager userManager;
    @EJB
    private PointManager pointManager;

    private int totalPoints = 0;
    private int totalPointsInArea = 0;

    private final NotificationBroadcasterSupport broadcaster;
    private long sequenceNumber;

    /**
     * Конструктор по умолчанию
     */
    public PointCounter() {
        this.broadcaster = new NotificationBroadcasterSupport();
        this.sequenceNumber = 1;
    }

    /**
     * Инициализация MBean-а
     */
    @PostConstruct
    public void init() {
        try {
            initializeFromDatabase();

            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("lab.monitoring:type=Lab4,name=PointCounter");
            mbs.registerMBean(this, name);
        } catch (Exception e) {
            System.err.println("Failed to register MBean: " + e.getMessage());
        }
    }

    private void initializeFromDatabase() {
        try {
            List<Point> allPoints = pointManager.getAllPoints(); // Нужно добавить этот метод в PointManager

            totalPoints = allPoints.size();
            totalPointsInArea = (int) allPoints.stream().filter(Point::isResult).count();

        } catch (Exception e) {
            System.err.println("Failed to initialize from database: " + e.getMessage());
        }
    }


    /**
     * Удаление MBean-а
     */
    @PreDestroy
    public void destroy() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("lab.monitoring:type=Lab4,name=PointCounter");

            if (mbs.isRegistered(name)) {
                mbs.unregisterMBean(name);
                System.out.println("MBean unregistered on destroy: " + name);
            }
        } catch (Exception e) {
            System.err.println("Failed to unregister MBean: " + e.getMessage());
        }
    }

    @Override
    public int getTotalPoints() {
        return totalPoints;
    }

    @Override
    public int getTotalPointsInArea() {
        return totalPointsInArea;
    }

    @Override
    public int countPointsForUser(String login) {
        User user = userManager.findUserByLogin(login);
        return pointManager.getPointsByUser(user).size();
    }

    @Override
    public int countPointsInAreaForUser(String login) {
        int count = 0;
        User user = userManager.findUserByLogin(login);
        List<Point> points = pointManager.getPointsByUser(user);
        for (Point point : points) {
            if (point.isResult()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback)
            throws IllegalArgumentException {
        broadcaster.addNotificationListener(listener, filter, handback);
    }

    @Override
    public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
        broadcaster.removeNotificationListener(listener);
    }

    @Override
    public void removeNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback)
            throws ListenerNotFoundException {
        broadcaster.removeNotificationListener(listener, filter, handback);
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        return new MBeanNotificationInfo[]{
                new MBeanNotificationInfo(
                        new String[]{"PointOutOfBounds"},
                        Notification.class.getName(),
                        "Notification when a point is out of bounds"
                )
        };
    }

    @Override
    public void pointAdded(Point point, String login) {
        totalPoints++;

        if (point.isResult()) {
            totalPointsInArea++;
        }

        if (isPointOutOfBounds(point)) {
            sendNotification(point, login);
        }
    }

    private void sendNotification(Point point, String login) {
        String message = String.format("User '%s' added point: x=%.2f, y=%.2f, r=%.2f,",
                login, point.getX(), point.getY(), point.getR());
        Notification notification = new Notification(
                "PointOutOfBounds",
                this,
                sequenceNumber++,
                System.currentTimeMillis(),
                message);
        Map<String, Object> pointData = new HashMap<>();
        pointData.put("x", point.getX());
        pointData.put("y", point.getY());
        pointData.put("r", point.getR());
        pointData.put("result", point.isResult());
        notification.setUserData(pointData);
        broadcaster.sendNotification(notification);
        System.out.println("Sent Notification: " + message);
    }

    private boolean isPointOutOfBounds(Point point) {
        return Math.abs(point.getX()) > Math.abs(point.getR()) || Math.abs(point.getY()) > Math.abs(point.getR());
    }


}
