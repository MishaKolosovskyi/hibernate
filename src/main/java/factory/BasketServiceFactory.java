package factory;

import service.BasketService;
import service.impl.BasketServiceImpl;

public class BasketServiceFactory {

    private static BasketService instance;

    private BasketServiceFactory(){
    }

    public static synchronized BasketService getInstance() {
        if (instance == null) {
            instance = new BasketServiceImpl();
        }
        return instance;
    }
}
