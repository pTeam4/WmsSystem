package vo;

import lombok.Data;

import java.sql.Timestamp;

//재고실사
@Data
public class StockCount {
    private int id;
    private int warehouseId;
    private Timestamp checkDate;
}
