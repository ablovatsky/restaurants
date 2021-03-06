package servlet.administration;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import itacademy.restaurants.model.Restaurant;
import itacademy.restaurants.service.ExceptionService;
import itacademy.restaurants.service.RestaurantService;
import itacademy.restaurants.service.impl.RestaurantDatabaseService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

@MultipartConfig(maxFileSize = 169999999)
@WebServlet(urlPatterns = "/loading/image")
public class LoadImageServlet extends HttpServlet {

    private static final String RESTAURANTS_URL = "/administration/restaurants";

    private RestaurantService restaurantService;

    public LoadImageServlet() {
        restaurantService = RestaurantDatabaseService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String imageURL = req.getParameter("imageURL");
        Path path = Paths.get(imageURL);
        byte[] data = Files.readAllBytes(path);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = req.getParameter("json");
        InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        JsonReader reader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
        reader.setLenient(true);
        Restaurant restaurant = new Gson().fromJson(reader, Restaurant.class);
        Part partFile = req.getPart("image");
        InputStream image = null;
        if (partFile != null) {
            long fileSize = partFile.getSize();
            String fileContent = partFile.getContentType();
            image = partFile.getInputStream();
        }
        restaurant.setLoadingImage(image);
        restaurantService.add(restaurant);

        HttpSession session = req.getSession();
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(RESTAURANTS_URL);
        session.setAttribute("routing", "edit");
        dispatcher.forward(req, resp);

    }

}
