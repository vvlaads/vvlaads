package lab.database;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Stateless
public class UserManager {
    @PersistenceContext(unitName = "userManager")
    private EntityManager entityManager;

    public void addUser(User user) {
        entityManager.persist(user);
    }

    public boolean check(User user) {
        String login = user.getLogin();
        String password = user.getPassword();
        User findedUser = findUserByLogin(login);
        if (findedUser == null) {
            addUser(user);
            return true;
        }
        if (password.equals(findedUser.getPassword())) {
            return true;
        }
        return false;
    }

    public User findUserByLogin(String login) {
        try {
            User user = entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }

}
