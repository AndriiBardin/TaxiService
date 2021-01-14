package taxi;

import taxi.lib.Injector;
import taxi.model.Manufacturer;
import taxi.services.ManufacturerService;

public class Main {
    private static Injector injector = Injector.getInstance("taxi");

    public static void main(String[] args) {
        ManufacturerService manufacturerService = (ManufacturerService)
                injector.getInstance(ManufacturerService.class);

        Manufacturer toyota = new Manufacturer("Toyota", "Japan, Toyota City");
        Manufacturer honda = new Manufacturer("Honda", "Japan");
        Manufacturer koenigsegg = new Manufacturer("Koenigsegg", "Sweden, Angelholm");

        manufacturerService.create(toyota);
        manufacturerService.create(honda);
        manufacturerService.create(koenigsegg);

        System.out.println(manufacturerService.getAll());

        manufacturerService.delete(3L);

        System.out.println(manufacturerService.getAll());

        Manufacturer manufacturerUpdate = manufacturerService.get(2L);
        manufacturerUpdate.setCountry("Japan, Minato City");

        System.out.println(manufacturerService.getAll());

    }
}
