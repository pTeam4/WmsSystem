package vo;

import lombok.Data;

import java.util.Date;

//운송장
@Data
public class Waybill {
    private int id;
    private int shippingOrdersId;
    private String vehicleNum;
    private Date departureDate;
    private Date arrivalDate;
}
