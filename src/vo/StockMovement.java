package vo;

import lombok.Data;

import java.util.Date;

@Data
public class StockMovement {
    private int id;
    private int productId;
    private String userId;
    private String statusCode;
    private Date requestDatetime;
    private Date approvedDatetime;
}