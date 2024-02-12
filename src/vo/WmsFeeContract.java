package vo;

import lombok.Data;

import java.util.Date;

//계약
@Data
public class WmsFeeContract {
    private int id;
    private Date contractDate;
    private String confirm;
    private int estimated_id;

}
