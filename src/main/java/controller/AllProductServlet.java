package controller;

import factory.ProductServiceFactory;
import service.ProductService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/product/all")
public class AllProductServlet extends HttpServlet {

    private ProductService productService = ProductServiceFactory.getInstance();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("products", productService.getAll());
        req.getRequestDispatcher("/all_products.jsp").forward(req, resp);
    }
}
