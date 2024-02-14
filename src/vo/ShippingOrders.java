package vo;

import lombok.Data;

import java.util.Date;

//출고 지시서
@Data
public class ShippingOrders {
    private int id;
    private int customerId;
    private String deliveryAddress;
    private Date orderDate;
    private Date deliveryDate;
    private int status;
    private int approvedStatus;
}