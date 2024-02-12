package vo;

import lombok.Data;

//재고
@Data
public class Stock {
    private int id;
    private int warehouseId;
    private int productId;
    private int quantity;
}
