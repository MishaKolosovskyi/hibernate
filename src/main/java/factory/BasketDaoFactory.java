package factory;

import dao.BasketDao;
import dao.impl.hibernate.BasketDaoImpl;

public class BasketDaoFactory {

    private static BasketDao instance;

    private BasketDaoFactory(){
    }

    public static synchronized BasketDao getInstance() {
        if (instance == null) {
            instance = new BasketDaoImpl();
        }
        return instance;
    }
}
