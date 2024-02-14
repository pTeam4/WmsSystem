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

    public User() {
        // 생성자 내용 작성
    }

    public User(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

}
