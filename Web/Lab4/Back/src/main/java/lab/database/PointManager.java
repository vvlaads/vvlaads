package lab.database;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class PointManager {
    @PersistenceContext(unitName = "pointManager")
    private EntityManager entityManager;

    public void addPoint(Point point) {
        entityManager.persist(point);
    }

    public boolean savePoint(Point point) {
        try {
            addPoint(point);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public List<Point> getPointsByUser(User user) {
        String jpql = "SELECT p FROM Point p WHERE p.user = :user";

        return entityManager.createQuery(jpql, Point.class)
                .setParameter("user", user)
                .getResultList();
    }
}
