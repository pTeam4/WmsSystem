package vo;

import lombok.Data;

//차량
@Data
public class Vehicle {
    private String num;
    private int deliveryDriverId;
    private String type;
    private String telephone;
    private int status;
}