package service.impl;

import dao.OrderDao;
import factory.OrderDaoFactory;
import model.Order;
import service.OrderService;

public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao = OrderDaoFactory.getInstance();

    @Override
    public void addOrder(Order order) {
        orderDao.addOrder(order);
    }
}
