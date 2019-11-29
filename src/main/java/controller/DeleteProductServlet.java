package controller;

import factory.ProductServiceFactory;
import model.Product;
import service.ProductService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/admin/product/delete")
public class DeleteProductServlet extends HttpServlet {

    private ProductService productService = ProductServiceFactory.getInstance();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        Optional<Product> optionalProduct = productService.getProductById(id);
        if (optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            productService.remove(product);
            resp.sendRedirect("/admin/product/all");
        }
    }
}
