package dao.impl.hibernate;

import dao.ProductDao;
import model.Product;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;
import java.util.List;
import java.util.Optional;

public class ProductDaoImpl implements ProductDao {

    private Logger logger = Logger.getLogger(ProductDaoImpl.class);

    @Override
    public List<Product> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Product", Product.class).list();
        }
    }

    @Override
    public void addProduct(Product product) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(product);
            transaction.commit();
            logger.info("this product was added");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.error("Can't add this product", e);
            }
        }
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Product product = session.get(Product.class, id);
            return Optional.of(product);
        }
    }

    @Override
    public void updateProduct(Product product) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Product newProduct = session.get(Product.class, product.getId());
            newProduct.setName(product.getName());
            newProduct.setDescription(product.getDescription());
            newProduct.setPrice(product.getPrice());
            session.update(newProduct);
            transaction.commit();
            logger.info("this product was updated");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.error("Can't update this product", e);
            }
        }
    }

    @Override
    public void remove(Product product) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(product);
            transaction.commit();
            logger.info("this product was removed");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.error("Can't remove this product");
            }
        }
    }
}
