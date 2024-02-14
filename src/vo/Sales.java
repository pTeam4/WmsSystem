package vo;

import lombok.Data;

import java.util.Date;

@Data
public class Sales {
    private int id;
    private int warehouseId;
    private int amount;
    private String type;
    private Date salesDate;
}
