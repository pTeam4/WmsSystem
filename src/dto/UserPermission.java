package dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserPermission {
    private String id;
    private String name;
    private Date birth;
    private String pw;
    private String email;
    private String tel;
    private String level;
    private String state;
}
