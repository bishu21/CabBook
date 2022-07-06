package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class Trip {
    Integer tripId;
    Location pickUpPoint;
    Location destinationPoint;
    String customerUserId;
    Integer carId;
    Double price;
    Status status;
    LocalDateTime startTime;
    LocalDateTime endtime;


    public enum Status {
        PENDING, STARTED, REACHED, PAID, FAILED
    }
}
