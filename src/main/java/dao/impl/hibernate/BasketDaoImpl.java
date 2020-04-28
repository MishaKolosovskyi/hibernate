package dao.impl.hibernate;

import dao.BasketDao;
import model.Basket;
import model.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtil;
import java.util.Optional;

public class BasketDaoImpl implements BasketDao {

    private Logger logger = Logger.getLogger(BasketDaoImpl.class);

    @Override
    public void addBasket(Basket basket) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(basket);
            transaction.commit();
            logger.info("this basket was added");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.error("Can't add this basket", e);
            }
        }
    }

    @Override
    public Optional<Long> getBasketIdByUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Query query = session.createQuery("FROM Basket WHERE user =:user");
            query.setParameter("user", user);
            Basket basket = (Basket) query.uniqueResult();
            logger.info("Found this basket id by user");
            return Optional.of(basket.getId());
        }catch (Exception e){
            logger.error("Can't find this basket id by user", e);
            return Optional.empty();
        }
    }
}
