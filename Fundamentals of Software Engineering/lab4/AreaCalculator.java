package lab.monitoring;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * MBean для подсчёта площади фигуры
 */
@Startup
@Singleton
public class AreaCalculator implements AreaCalculatorMBean {

    private double lastCalculatedArea = 0;

    /**
     * Инициализация MBean-а
     */
    @PostConstruct
    public void init() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("lab.monitoring:type=Lab4,name=AreaCalculator");
            mbs.registerMBean(this, name);
        } catch (Exception e) {
            System.err.println("Failed to register MBean: " + e.getMessage());
        }
    }

    /**
     * Удаление MBean-а
     */
    @PreDestroy
    public void destroy() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("lab.monitoring:type=Lab4,name=AreaCalculator");

            if (mbs.isRegistered(name)) {
                mbs.unregisterMBean(name);
                System.out.println("MBean unregistered on destroy: " + name);
            }
        } catch (Exception e) {
            System.err.println("Failed to unregister MBean: " + e.getMessage());
        }
    }

    @Override
    public double calculateArea(double r) {
        if (r == 0) {
            return 0;
        }
        r = Math.abs(r);
        double square = 0;
        square += r * r / 2;
        square += r * r / 4;
        square += Math.PI * r * r / 4;
        lastCalculatedArea = square;
        return square;
    }

    @Override
    public double getLastCalculatedArea() {
        return lastCalculatedArea;
    }
}
