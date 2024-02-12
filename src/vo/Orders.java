package vo;

import lombok.Data;

import java.util.Date;

@Data
public class Orders {
    private int id;
    private int customer_id;
    private Date orderDate;
}
