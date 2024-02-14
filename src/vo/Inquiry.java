package vo;

import lombok.Data;

import java.sql.Blob;
import java.util.Date;

//문의글
@Data
public class Inquiry{
    private int postNum;
    private String postType;
    private String postTitle;
    private String userId;
    private String userName;
    private String postContent;
    private Date postDate;
    private String response;
}
