package vo;

import lombok.Data;

import java.util.Date;

@Data
public class Shipments {
    private int id;
    private int orderId;
    private Date shipmentDate;
}
