package vo;

import lombok.Data;

import java.sql.Blob;

//공지사항
@Data
public class Notice{
    private int no;
    private String administratorId;
    private String type;
    private String title;
    private String content;
    private int userId;

}
