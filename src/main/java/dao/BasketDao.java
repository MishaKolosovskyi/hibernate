package dao;

import model.Basket;
import model.User;
import java.util.Optional;

public interface BasketDao {

    void addBasket(Basket basket);
    Optional<Long> getBasketIdByUser(User user);
}
