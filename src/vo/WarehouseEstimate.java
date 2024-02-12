package vo;

import lombok.Data;

import java.util.Date;

//견적
@Data
public class WarehouseEstimate {
    private int id;
    private int estimatedCost;
    private Date estimatedDate;
    private String details;
    private int vendorId;
    private int feeInfoId;
    private int warehouseId;
}
