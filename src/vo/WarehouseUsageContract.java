package vo;

import lombok.Data;

import java.util.Date;

//계약
@Data
public class WarehouseUsageContract {
    private int id;
    private Date contractDate;
    private String confirm;
    private int warehouseEstimatedId;
    private int vendorId;
}
