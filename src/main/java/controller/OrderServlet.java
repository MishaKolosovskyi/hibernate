package controller;

import factory.BasketServiceFactory;
import model.Basket;
import model.Order;
import model.User;
import service.BasketService;
import service.MailService;
import service.impl.MailServiceImpl;
import utils.CodeGeneratorUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/user/order")
public class OrderServlet extends HttpServlet {

    private BasketService basketService = BasketServiceFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("name", user.getName());
        req.setAttribute("surname", user.getSurname());
        req.setAttribute("mail", user.getMail());
        req.getRequestDispatcher("/order.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String mail = req.getParameter("mail");
        String phoneNumber = req.getParameter("phoneNumber");
        String address = req.getParameter("address");
        String code = CodeGeneratorUtil.codeGenerator();
        MailService mailService = new MailServiceImpl();
        mailService.sendCode(code, mail);
        req.getSession().setAttribute("code", code);
        Basket basket = (Basket) req.getSession().getAttribute("basket");
        Optional<Long> optionalId = basketService.getBasketIdByUser(basket.getUser());
        if (optionalId.isPresent()) {
            Long id = optionalId.get();
            basket.setId(id);
        }
        Order order = new Order(name, surname, mail, phoneNumber, address, code, basket);
        req.getSession().setAttribute("order", order);
        resp.sendRedirect("/user/code/enter");
    }
}
