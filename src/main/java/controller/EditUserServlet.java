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
import java.util.Optional;

@WebServlet("/admin/user/edit")
public class EditUserServlet extends HttpServlet {

    private UserService userService = UserServiceFactory.getInstance();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String mail = req.getParameter("mail");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeatPassword");
        if (password.equals(repeatPassword)) {
            User user = new User(id, name, surname, mail, password);
            userService.updateUser(user);
            resp.sendRedirect("/admin/user/all");
        } else {
            req.setAttribute("passwordError", "Different passwords");
            req.setAttribute("name", name);
            req.setAttribute("surname", surname);
            req.setAttribute("mail", mail);
            req.setAttribute("userId", id);
            req.getRequestDispatcher("/edit_user.jsp").forward(req, resp);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            req.setAttribute("name", user.getName());
            req.setAttribute("surname", user.getSurname());
            req.setAttribute("mail", user.getMail());
            req.setAttribute("userId", id);
            req.getRequestDispatcher("/edit_user.jsp").forward(req, resp);
        }
    }
}
