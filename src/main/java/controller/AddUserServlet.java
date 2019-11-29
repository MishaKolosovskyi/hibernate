package controller;

import factory.UserServiceFactory;
import model.User;
import service.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/user/add")
public class AddUserServlet extends HttpServlet {

    private UserService userService = UserServiceFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/add_user.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String mail = req.getParameter("mail");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeatPassword");
        String role = req.getParameter("role");
        User user = new User(name, surname, mail, password, role);
        if (password.equals(repeatPassword)){
            userService.addUser(user);
            resp.sendRedirect("/admin/user/all");
        }else {
            req.setAttribute("passwordError","Different passwords");
            req.setAttribute("name", user.getName());
            req.setAttribute("surname", user.getSurname());
            req.setAttribute("mail", user.getMail());
            req.getRequestDispatcher("/add_user.jsp").forward(req, resp);
        }
    }
}
