package vo;

import lombok.Data;

//상품
@Data
public class Product {
    private int id;
    private String name;
    private String brand;
    private String type;
    private int price;
    private int salePrice;
    private int quantity;
}