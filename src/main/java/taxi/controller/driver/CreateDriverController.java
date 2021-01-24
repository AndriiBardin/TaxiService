package taxi.controller.driver;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import taxi.lib.Injector;
import taxi.model.Driver;
import taxi.services.DriverService;

public class CreateDriverController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("taxi");
    private final DriverService driverService =
            (DriverService) injector.getInstance(DriverService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/driver/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String name = req.getParameter("name");
        String licence = req.getParameter("licence");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("password-repeat");
        if (password.equals(repeatPassword)) {
            Driver driver = new Driver(name, licence, login, password);
            driverService.create(driver);
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("message", "Passwords do not match");
            req.getRequestDispatcher("/WEB-INF/view/driver/create.jsp").forward(req, resp);
        }
    }
}
