package controller;

import factory.ProductServiceFactory;
import model.Basket;
import model.Product;
import model.User;
import service.ProductService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@WebServlet("/user/product/buy")
public class BuyProductServlet extends HttpServlet {

    private ProductService productService = ProductServiceFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("products", productService.getAll());
        req.getRequestDispatcher("/buy_product.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        Optional<Product> optionalProduct = productService.getProductById(id);
        User user = (User) req.getSession().getAttribute("user");
        Basket basketFromSession = (Basket) req.getSession().getAttribute("basket");
        if (Objects.isNull(basketFromSession)){
            basketFromSession = new Basket(user, new ArrayList<>());
        }
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            basketFromSession.getProducts().add(product);
            req.setAttribute("productCounter", basketFromSession.getProducts().size());
            req.getSession().setAttribute("basket", basketFromSession);
        }
        req.setAttribute("products", productService.getAll());
        req.getRequestDispatcher("/buy_product.jsp").forward(req, resp);
    }
}
