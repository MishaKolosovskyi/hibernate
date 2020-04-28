package controller;

import factory.ProductServiceFactory;
import factory.UserServiceFactory;
import model.Product;
import model.User;
import service.ProductService;
import service.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(value ="/", loadOnStartup = 1)
public class InitServlet extends HttpServlet {

    private ProductService productService = ProductServiceFactory.getInstance();
    private UserService userService = UserServiceFactory.getInstance();

    @Override
    public void init() throws ServletException {
        User admin = new User("1", "1", "1@net", "1", "admin");
        User user = new User("22", "2", "2@net", "2", "user");
        Product product = new Product("wine","red", 20.0);
        userService.addUser(user);
        userService.addUser(admin);
        productService.addProduct(product);
    }
}
