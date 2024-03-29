package vo;

import lombok.Data;

import java.util.Date;

//사용자
@Data
public class User {
    private String id;
    private String name;
    private Date birth;
    private String pw;
    private String email;
    private String tel;
    private int permission;
    private int status;
}
