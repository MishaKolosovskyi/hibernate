package factory;

import service.impl.OrderServiceImpl;

public class OrderServiceFactory {

        private static OrderServiceImpl instance;

        private OrderServiceFactory(){
        }

        public static synchronized OrderServiceImpl getInstance() {
            if (instance == null) {
                instance = new OrderServiceImpl();
            }
            return instance;
        }
    }
