package services;

import lombok.AllArgsConstructor;
import models.Trip;

@AllArgsConstructor
public class PaymentService {
    private BookingService bookingService;

    public void payment(Integer tripId) {
        Trip trip = bookingService.getTripDetails(tripId);
        trip.setStatus(Trip.Status.PAID);
    }
}
