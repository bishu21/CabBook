import models.Car;
import models.Location;
import models.Trip;
import models.User;
import services.BookingService;

public class CabBookApp {
    public static void main(String[] args) throws InterruptedException {
        BookingService bookingService = new BookingService();
        User user = new User("Bishwendra", "9571777319", User.Type.CUSTOMER);
        User user1 = new User("Suman", "9571777319", User.Type.CUSTOMER);
        User user2 = new User("Manmohan", "9571777319", User.Type.DRIVER);
        User user3 = new User("Suraj", "9571777319", User.Type.DRIVER);

        bookingService.registerUser(user);
        bookingService.registerUser(user1);
        bookingService.registerUser(user2);
        bookingService.registerUser(user3);

        Car car = new Car(1, "RJ26CA8923", new Location(0, 0), true, "Manmohan");
        Car car1 = new Car(2, "RJ26CA8923", new Location(0, 0), true, "Suraj");

        bookingService.registerCar(car);
        bookingService.registerCar(car1);
        Location pickUp = new Location(1, 1);
        Location drop = new Location(10, 10);
        System.out.println(bookingService.getAvailableCars(pickUp));
        System.out.println();
        Trip trip = bookingService.bookCab(1, "Bishwnedra", pickUp, drop);

        System.out.println(trip);
        System.out.println();
        System.out.println(bookingService.startTrip(trip.getTripId()));

        Thread.sleep(10000);
        System.out.println();

        bookingService.carsLocation();
        System.out.println(bookingService.endTrip(trip.getTripId()));

        System.out.println();
        bookingService.carsLocation();

    }
}
