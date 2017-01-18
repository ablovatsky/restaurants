package servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by VAblovatsky on 17.01.2017.
 */

@WebServlet(urlPatterns = "/1111")
public class MainServlet extends HttpServlet {

    private static final String RESTAURANTS_URL = "/WEB-INF/views/content/restaurants.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(RESTAURANTS_URL);
        dispatcher.forward(req, resp);
    }
}