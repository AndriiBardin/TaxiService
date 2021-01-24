package taxi.security;

import taxi.exception.AuthenticationException;
import taxi.lib.Inject;
import taxi.lib.Service;
import taxi.model.Driver;
import taxi.services.DriverService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private DriverService driverService;

    @Override
    public Driver login(String login, String password) throws AuthenticationException {
        Driver driverFromDB = driverService.getByLogin(login)
                .orElseThrow(() -> new AuthenticationException("Invalid login"));
        if (driverFromDB.getPassword().equals(password)) {
            return driverFromDB;
        }
        throw new AuthenticationException("Invalid password");
    }
}
