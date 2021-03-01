package taxi.services;

import java.util.Optional;
import taxi.model.Driver;

public interface DriverService extends GenericService<Driver, Long> {

    Optional<Driver> getByLogin(String login);
}
