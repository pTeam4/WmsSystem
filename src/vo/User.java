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



    // 정적 변수로 유일한 인스턴스를 저장합니다.

    public User() {
        // 생성자 내용 작성
    }

    public User(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

//    public User(String id, String name, Date birth, String pw, String email, String tel, String permission, String status) {
//        this.id = id;
//        this.name = name;
//        this.birth = birth;
//        this.pw = pw;
//        this.email = email;
//        this.tel = tel;
//        this.permission = permission;
//        this.status = status;
//    }



}
