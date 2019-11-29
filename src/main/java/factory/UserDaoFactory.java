package factory;

import dao.UserDao;
import dao.impl.hibernate.UserDaoImpl;

public class UserDaoFactory {

    private static UserDao instance;

    private UserDaoFactory(){
    }

    public static synchronized UserDao getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }
}
