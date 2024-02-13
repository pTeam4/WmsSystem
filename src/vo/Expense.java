package vo;

import lombok.Data;
import java.util.Date;
//지출

@Data
public class Expense {
    private int id;
    private int warehouseId;
    private String type;
    private Date expenseDate;
    private int cost;
}
