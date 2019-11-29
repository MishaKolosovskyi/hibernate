package controller;

import factory.OrderServiceFactory;
import model.Order;
import service.OrderService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/code/enter")
public class CodeServlet extends HttpServlet {

    private OrderService orderService = OrderServiceFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/user_enter_code.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String code = (String) req.getSession().getAttribute("code");
        String userCode = req.getParameter("code");
        if (userCode.equals(code)){
            req.setAttribute("codeMassage", "The code was added");
            Order order = (Order) req.getSession().getAttribute("order");
            orderService.addOrder(order);
            resp.sendRedirect("/user/product/buy");
        }else {
            req.setAttribute("codeMassage", "Different codes");
            resp.sendRedirect("/user/code/enter");
        }
    }
}
