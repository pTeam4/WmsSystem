package vo;

import lombok.Data;

import java.util.Date;

//사용자
@Data
public class User {
    private int no;
    private int membershipState;
    private int adminLevel;
    private String name;
    private Date birth;
    private String id;
    private String pw;
    private String email;
    private String tel;
}
