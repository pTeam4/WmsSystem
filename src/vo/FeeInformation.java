package vo;

import lombok.Data;

//요금 안내
@Data
public class FeeInformation {
    private int id;
    private String type;
    private String description;
}
