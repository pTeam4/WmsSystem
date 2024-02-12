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
    private int userNo;
    private int customerId;
    private String postContent;
    private Blob postAttach;
    private Date postDate;
    private String response;
}
