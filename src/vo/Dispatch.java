package vo;

import lombok.Data;

import java.util.Date;

//배차
@Data
public class Dispatch {
    private int id;
    private String vehicleNum;
    private int warehouseId;
    private String vehicleStatus;
    private Date requestDate;
    private Date dispatchDate;
}
