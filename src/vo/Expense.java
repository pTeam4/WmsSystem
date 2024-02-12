package vo;

import lombok.Data;

@Data
public class Expense {
    private int id;
    private int warehouseId;
    private String type;
    private int cost;
}
