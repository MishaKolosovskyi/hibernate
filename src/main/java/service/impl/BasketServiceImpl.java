package service.impl;

import dao.BasketDao;
import factory.BasketDaoFactory;
import model.Basket;
import model.User;
import service.BasketService;
import java.util.Optional;

public class BasketServiceImpl implements BasketService {

    private final BasketDao basketDao = BasketDaoFactory.getInstance();

    @Override
    public void addBasket(Basket basket) {
        basketDao.addBasket(basket);
    }

    @Override
    public Optional<Long> getBasketIdByUser(User user) {
        return basketDao.getBasketIdByUser(user);
    }

}
