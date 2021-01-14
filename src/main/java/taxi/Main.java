package taxi;

import taxi.lib.Injector;
import taxi.model.Car;
import taxi.model.Driver;
import taxi.model.Manufacturer;
import taxi.services.CarService;
import taxi.services.DriverService;
import taxi.services.ManufacturerService;

public class Main {
    private static Injector injector = Injector.getInstance("taxi");

    public static void main(String[] args) {
        ManufacturerService manufacturerService = (ManufacturerService)
                injector.getInstance(ManufacturerService.class);
        DriverService driverService = (DriverService)
                injector.getInstance(DriverService.class);
        CarService carService = (CarService)
                injector.getInstance(CarService.class);

        Manufacturer toyota = new Manufacturer("Toyota", "Japan, Toyota City");
        Manufacturer honda = new Manufacturer("Honda", "Japan");
        Manufacturer koenigsegg = new Manufacturer("Koenigsegg", "Sweden, Angelholm");
        Manufacturer nissan = new Manufacturer("Nissan", "Japan");
        Manufacturer bmw = new Manufacturer("BMW", "Germany");

        Driver one = new Driver("001", "0001");
        Driver two = new Driver("002", "0002");
        Driver three = new Driver("003", "0003");
        Driver four = new Driver("004", "0005");
        Driver five = new Driver("005", "0005");

        Car yaris = new Car("Yaris GR", toyota);
        Car markII = new Car("MarkII", toyota);
        Car civic = new Car("Civic type r", honda);
        Car nsx = new Car("NSX", honda);
        Car gemera  = new Car("Gemera", koenigsegg);
        Car skyline = new Car("Skyline R35", nissan);


        manufacturerService.create(toyota);
        manufacturerService.create(honda);
        manufacturerService.create(koenigsegg);
        manufacturerService.create(nissan);
        manufacturerService.create(bmw);

        System.out.println(manufacturerService.getAll());

        manufacturerService.delete(5L);

        Manufacturer manufacturerUpdate = manufacturerService.get(2L);
        manufacturerUpdate.setCountry("Japan, Minato City");

        System.out.println(manufacturerService.getAll());

        driverService.create(one);
        driverService.create(two);
        driverService.create(three);
        driverService.create(four);
        driverService.create(five);

        Driver driverUpdate = driverService.update(two);
        driverUpdate.setLicenceNumber("39800");
        System.out.println(driverService.get(2L));

        carService.create(yaris);
        carService.create(markII);
        carService.create(civic);
        carService.create(nsx);
        carService.create(gemera);
        carService.create(skyline);

        carService.addDriverToCar(one, civic);
        carService.addDriverToCar(one, gemera);
        carService.addDriverToCar(two, gemera);
        carService.addDriverToCar(two, skyline);
        carService.addDriverToCar(two, yaris);
        carService.addDriverToCar(three, markII);
        carService.addDriverToCar(four, skyline);
        carService.addDriverToCar(four, nsx);

        carService.delete(5L);

        System.out.println(carService.getAllByDriver(2L));

        carService.removeDriverFromCar(two,yaris);

        System.out.println(carService.getAllByDriver(2L));

    }
}
