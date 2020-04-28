package factory;

import dao.OrderDao;
import dao.impl.hibernate.OrderDaoImpl;

public class OrderDaoFactory {

    private static OrderDao instance;

    private OrderDaoFactory() {
    }

    public static synchronized OrderDao getInstance() {
        if (instance == null) {
            instance = new OrderDaoImpl();
        }
        return instance;
    }
}
