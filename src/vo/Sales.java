package vo;

import lombok.Data;

@Data
public class Sales {
    private int id;
    private int warehouseId;
    private int amount;
    private String type;
}
