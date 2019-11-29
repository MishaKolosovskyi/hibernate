package controller;

import factory.UserServiceFactory;
import model.User;
import org.apache.commons.codec.digest.DigestUtils;
import service.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService = UserServiceFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String mail = req.getParameter("mail");
        String password = req.getParameter("password");
        Optional<User> optionalUser = userService.getUserByMail(mail);
        User user = null;
        if(optionalUser.isPresent()){
            user = optionalUser.get();
        }
        String encryptedPassword = DigestUtils.sha256Hex(password);
        if (user != null && user.getPassword().equals(encryptedPassword)) {
            req.getSession().setAttribute("user",user);
            if (user.getRole().equals("admin")) {
                resp.sendRedirect("/admin/user/all");
            }else {
                resp.sendRedirect("/user/product/buy");
            }
        } else {
            req.setAttribute("mail", mail);
            req.setAttribute("error", "The email and password you’ve entered doesn’t match any account.");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
