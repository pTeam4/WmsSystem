package dao;

import config.JdbcConnection;
import vo.Inquiry;

import java.sql.*;

//문의글
public class InquiryDao {
    Connection conn;
    public void inquiryInsert(Inquiry inquiry){
        conn = JdbcConnection.getInstance().getConnection();
        String sql = "Insert into Inquiry(post_type, post_title, user_id, user_name, post_content, post_date)"
                + "values (?, ?, ?, ?, ?, now())";

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            pstmt.setString(1, inquiry.getPostType());
            pstmt.setString(2,inquiry.getPostTitle());
            pstmt.setString(3,inquiry.getUserId());
            pstmt.setString(4, inquiry.getUserName());
            pstmt.setString(5, inquiry.getPostContent());
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void inquirySelect()
    {
        conn = JdbcConnection.getInstance().getConnection();
        System.out.println("[문의 게시판 게시물 목록]");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.printf("%-6s%-10s%-12s%-15s%-25s%-16s%-16s%-16s\n", "게시물번호", "문의유형", "제목", "ID", "작성자이름", "내용", "작성일", "답변");
        System.out.println("----------------------------------------------------------------------------------------");

        String sql = "SELECT * FROM inquiry";

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
        ) {

            while (rs.next()) {
                String content = rs.getString("post_content").replace("\r\n", " ");
                if(content.length() > 10)
                {
                    content = content.substring(0, 10) + "...";
                }
                String response = rs.getString("response");
                if(response != null)
                {
                    response = response.replace("\r\n", " ");
                    if(response.length() > 10)
                    {
                        response = response.substring(0, 10) + "...";
                    }
                }
                else {
                    response = "답변 대기중";
                }

                System.out.printf("%-6s%-10s%-12s%-15s%-25s%-16s%-16s%-16s\n",
                        rs.getInt("post_num"),
                        rs.getString("post_type"),
                        rs.getString("post_title"),
                        rs.getString("user_id"),
                        rs.getString("user_name"),
                        content,
                        rs.getDate("post_date"),
                        response);
            }

            System.out.println(
                    "----------------------------------------------------------------------------------------\n"
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void inquirySelectOne(int postNum)
    {

    }

    public void inquiryDelete(int postNum)
    {

    }

    public void inquiryUpdate(int postNum)
    {

    }
}
