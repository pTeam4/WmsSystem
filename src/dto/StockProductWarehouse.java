package dto;

import lombok.Data;

@Data
public class StockProductWarehouse {
    private int stockId;
    private String productName;
    private int stockQuantity;
    private String warehouseName;
    private String warehouseLocation;
}
