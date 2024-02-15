package dto;

import lombok.Data;

@Data
public class StockInfo {
    private int stockId;
    private String productName;
    private int stockQuantity;
    private String warehouseName;
    private String warehouseLocation;
}
