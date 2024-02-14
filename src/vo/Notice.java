package vo;

import lombok.Data;

import java.sql.Blob;
import java.util.Date;

//공지사항
@Data
public class Notice{
    private int no;
    private String type;
    private String title;
    private String content;
    private String userId;
    private Date noticeDate;

}
