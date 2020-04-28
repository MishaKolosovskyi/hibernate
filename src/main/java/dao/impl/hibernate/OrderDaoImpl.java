package dao.impl.hibernate;

import dao.OrderDao;
import model.Order;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

public class OrderDaoImpl implements OrderDao {

    private Logger logger = Logger.getLogger(OrderDaoImpl.class);

    @Override
    public void addOrder(Order order) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
            logger.info("this order was added");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.error("Can't add this order", e);
            }
        }
    }
}
