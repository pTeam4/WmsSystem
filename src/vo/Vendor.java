package vo;

import lombok.Data;

//거래처
@Data
public class Vendor {
    private int id;
    private String name;
    private String address;
    private String telephone;
}
