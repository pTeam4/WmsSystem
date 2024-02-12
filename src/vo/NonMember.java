package vo;

import lombok.Data;

//비회원
@Data
public class NonMember extends User{
    private int id;
    private int userId;
}
