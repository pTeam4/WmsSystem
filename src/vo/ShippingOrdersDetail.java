package vo;

import lombok.Data;

@Data
public class ShippingOrdersDetail {
    private int id;
    private int shippingOrdersId;
    private int productId;
    private int quantity;
}
