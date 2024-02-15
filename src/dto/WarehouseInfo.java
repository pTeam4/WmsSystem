package dto;

import lombok.Data;

@Data
public class WarehouseInfo {
    private int warehouseId;
    private String warehouseName;
    private String warehouseLocation;
    private String warehouseType;
    private String productName;
    private int stockQuantity;
}
