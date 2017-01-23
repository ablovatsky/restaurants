package servlet.content;

import itacademy.restaurants.model.Restaurant;
import itacademy.restaurants.service.RestaurantService;
import itacademy.restaurants.service.impl.RestaurantDatabaseService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@WebServlet(urlPatterns = "/restaurant")
public class RestaurantServlet extends HttpServlet{

    private RestaurantService restaurantService;

    private static final String RESTAURANT_URL = "/WEB-INF/views/content/restaurant.jsp";

    public RestaurantServlet() {
        //restaurantService = RestaurantDatabaseService.getInstance();
        Constructor[] constructors = RestaurantDatabaseService.class.getConstructors();
        for(Constructor constructor: constructors) {
            constructor.setAccessible(true);
            try {
                restaurantService = (RestaurantDatabaseService) constructor.newInstance();
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        Restaurant restaurant = restaurantService.getById(id);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(RESTAURANT_URL);
        req.setAttribute("restaurant", restaurant);
        dispatcher.forward(req, resp);
    }
}