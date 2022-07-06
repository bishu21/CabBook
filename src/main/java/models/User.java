package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    String userId;
    String phoneNo;
    Type type;
    public enum Type {
        DRIVER, CUSTOMER
    }
}
