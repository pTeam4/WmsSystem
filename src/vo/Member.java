package vo;

import lombok.Data;

//회원 = 쇼핑몰 관리자를 말함
@Data
public class Member {
    private int id;
    private int userId;
    private String shopName;
    private int coRegNum;
    private String shopAddress;
    private String businessType;
    private int onlineBusinessNumber;
    private String fax;

}
