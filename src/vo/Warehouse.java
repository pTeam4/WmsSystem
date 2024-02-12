package vo;

import lombok.Data;

//창고
@Data
public class Warehouse {
    private int id;
    private String name;
    private String location;
    private String type;
}
