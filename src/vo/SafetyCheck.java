package vo;

import lombok.Data;

import java.util.Date;

//안전검사
@Data
public class SafetyCheck{
    private int id;
    private int containerId;
    private String containerStatus;
    private Date checkDate;
    private String checker;
}
