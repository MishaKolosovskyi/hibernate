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

@WebServlet("/admin/product/edit")
public class EditProductServlet extends HttpServlet {

    private ProductService productService = ProductServiceFactory.getInstance();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        Double price = Double.valueOf(req.getParameter("price"));
        Long id = Long.valueOf(req.getParameter("id"));
        Product product = new Product(id, name, description, price);
        productService.updateProduct(product);
        resp.sendRedirect("/admin/product/all");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        Optional<Product> optionalProduct = productService.getProductById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            req.setAttribute("name", product.getName());
            req.setAttribute("description", product.getDescription());
            req.setAttribute("price", product.getPrice());
            req.setAttribute("productId",id);
            req.getRequestDispatcher("/edit_product.jsp").forward(req, resp);
        }
    }
}
