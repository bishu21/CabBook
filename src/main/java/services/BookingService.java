package services;

import models.Car;
import models.Location;
import models.Trip;
import models.User;

import java.awt.geom.Point2D;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookingService {
    Map<Integer, Car> carMap = new HashMap<>();
    Map<String, User> userMap = new HashMap<>();
    Map<Integer, Trip> tripMap = new HashMap<>();
    int count=1;

    public void carsLocation() {
        System.out.println("System has all these available cars !!");
        System.out.println(carMap.values());
    }

    public List<Car> getAvailableCars(Location x) {
        System.out.println("Availabe cars for this Location = "+x);
        return carMap.values().stream().filter(item -> item.getIsAvailable() == true).filter(item ->
                Point2D.distance(x.getX(), x.getY(), item.getCarLocation().getX(),
                 item.getCarLocation().getY()) < 10.0).collect(Collectors.toList());
    }

    public void setCarAvailability(Integer carId, Boolean isAvailable) {
        if(!carMap.containsKey(carId)){
            throw new RuntimeException("Car is not found in systemn with id = "+ carId);
        }
        carMap.get(carId).setIsAvailable(isAvailable);
    }


    public Trip bookCab(Integer carId, String userId, Location pickUp, Location destination) {

        System.out.println("Booking the cab with cabId="+carId+" with userId="+userId);

        if(!carMap.containsKey(carId) || carMap.get(carId).getIsAvailable() == false) {
            throw new RuntimeException("Car is not availabe for booking , carId = "+ carId);
        }

        Trip trip = new Trip();
        trip.setTripId(count++);
        trip.setCustomerUserId(userId);
        trip.setCarId(carId);
        trip.setStatus(Trip.Status.PENDING);
        trip.setPickUpPoint(pickUp);
        trip.setDestinationPoint(destination);

        tripMap.put(trip.getTripId(), trip);

        setCarAvailability(carId, false);


        return trip;
    }

    public Trip startTrip(Integer tripId) {
        if (!tripMap.containsKey(tripId)) {
            throw new RuntimeException("Trip is not exist for tripId = "+ tripId);
        }
        tripMap.get(tripId).setStatus(Trip.Status.STARTED);
        tripMap.get(tripId).setStartTime(LocalDateTime.now());
        carMap.get(tripMap.get(tripId).getCarId()).setCarLocation(tripMap.get(tripId).getPickUpPoint());
        return tripMap.get(tripId);
    }

    public Trip endTrip(Integer tripId) {
        if (!tripMap.containsKey(tripId)) {
            throw new RuntimeException("Trip is not exist for tripId = "+ tripId);
        }
        Trip trip = tripMap.get(tripId);
        trip.setStatus(Trip.Status.REACHED);
        trip.setEndtime(LocalDateTime.now());
        double price = 10 * Point2D.distance(trip.getPickUpPoint().getX(), trip.getPickUpPoint().getY(),
                trip.getDestinationPoint().getX(), trip.getDestinationPoint().getY()) +
                2 * ChronoUnit.MINUTES.between(trip.getStartTime(), trip.getEndtime());
        trip.setPrice(price);

        carMap.get(tripMap.get(tripId).getCarId()).setCarLocation(tripMap.get(tripId).getDestinationPoint());

       setCarAvailability(tripMap.get(tripId).getCarId(), true);

        return trip;
    }

    public Trip getTripDetails(Integer tripId) {
        if (!tripMap.containsKey(tripId)) {
            throw new RuntimeException("Trip is not exist for tripId = "+ tripId);
        }
        return tripMap.get(tripId);
    }

    public void registerUser(User user) {
        System.out.println("Register the user = "+ user);
        if(userMap.containsKey(user.getUserId())) {
            throw new RuntimeException("Can not insert same user to system , userId = "+ user.getUserId());
        }
        userMap.put(user.getUserId(), user);
    }

    public void registerCar(Car car) {
        System.out.println("Register the car = " + car);
        if(carMap.containsKey(car.getCarId())) {
            throw new RuntimeException("Can not insert same car to system , carId = "+ car.getCarId());
        }
        carMap.put(car.getCarId(), car);
    }

}
