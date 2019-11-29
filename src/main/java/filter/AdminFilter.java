package filter;

import model.User;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/admin/*")
public class AdminFilter implements Filter{

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) resp;
        User userFromSession = (User) httpRequest.getSession().getAttribute("user");
        if (userFromSession != null && userFromSession.getRole().equals("admin")) {
            chain.doFilter(req, resp);
        } else {
            httpResponse.sendRedirect("/login");
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }
}
