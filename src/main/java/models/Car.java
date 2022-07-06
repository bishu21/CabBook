package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Car {
    Integer carId;
    String carNumber;
    Location carLocation;
    Boolean isAvailable;
    String userId;
}
