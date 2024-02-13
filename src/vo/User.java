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
    private String permission;
    private String status;

    // 정적 변수로 유일한 인스턴스를 저장합니다.
    private static User instance;

    // 생성자를 private로 선언하여 외부에서 직접 인스턴스를 생성하는 것을 막습니다.
    private User() {
        // 생성자 내용 작성
    }

    // 인스턴스를 반환하는 정적 메서드를 제공합니다.
    public static User getInstance() {
        // 인스턴스가 아직 생성되지 않은 경우에만 생성합니다.
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public void logout() {
        this.id = null;
        this.pw = null;
    }

}
