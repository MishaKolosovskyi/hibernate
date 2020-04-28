package controller;

import factory.BasketServiceFactory;
import model.Basket;
import service.BasketService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/basket")
public class BasketServlet extends HttpServlet {

    private BasketService basketService = BasketServiceFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Basket basket = (Basket) req.getSession().getAttribute("basket");
        basketService.addBasket(basket);
        req.setAttribute("productsInBasket", basket.getProducts());
        req.getRequestDispatcher("basket.jsp").forward(req, resp);
    }
}
