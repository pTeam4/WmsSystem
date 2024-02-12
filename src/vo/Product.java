package vo;

import lombok.Data;

import java.sql.Blob;

//상품
@Data
public class Product {
    private int id;
    private int brandId;
    private Blob img;
    private String name;
    private String type;
    private int price;
    private int salePrice;
}
