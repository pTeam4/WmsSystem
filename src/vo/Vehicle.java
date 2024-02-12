package vo;

import lombok.Data;

//차량
@Data
public class Vehicle {
    private String num;
    private int driverId;
    private String type;
    private int contact1;
    private int contact2;
}
