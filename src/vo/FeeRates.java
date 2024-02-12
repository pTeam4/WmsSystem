package vo;

import lombok.Data;

@Data
public class FeeRates {
    private int id;
    private String durationType;
    private int feeAmount;
    private int feeInfoId;
}
