package dao.impl.hibernate;

import dao.UserDao;
import model.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Optional;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

public class UserDaoImpl implements UserDao {

    private Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public List<User> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).list();
        }
    }

    @Override
    public void addUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            user.setPassword(sha256Hex(user.getPassword()));
            session.save(user);
            logger.info("this user was added to the data");
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.error("Can't save this user", e);
            }
        }
    }

    @Override
    public Optional<User> getUserByMail(String mail) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM User WHERE mail =:mail");
            query.setParameter("mail", mail);
            User user = (User) query.uniqueResult();
            logger.info("this user is present");
            return Optional.of(user);
        } catch (Exception e) {
            logger.error("Can't find this user by mail", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUserById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            return Optional.of(user);
        }
    }

    @Override
    public void updateUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User newUser = session.get(User.class, user.getId());
            newUser.setName(user.getName());
            newUser.setSurname(user.getSurname());
            newUser.setMail(user.getMail());
            newUser.setPassword(sha256Hex(user.getPassword()));
            session.update(newUser);
            transaction.commit();
            logger.info("this user was updated");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.error("Can't update this user", e);
            }
        }
    }

    @Override
    public void remove(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
            logger.info("this user was removed");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.error("Can't remove this user");
            }
        }
    }
}
