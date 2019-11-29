package service;

import model.Basket;
import model.User;
import java.util.Optional;

public interface BasketService {

    void addBasket(Basket basket);
    Optional<Long> getBasketIdByUser(User user);
}
