package dao;

import config.JdbcConnection;
import vo.Inquiry;

import java.sql.*;
import java.util.ArrayList;

//문의글
public class InquiryDao {
    Connection conn;

    public int inquiryInsert(Inquiry inquiry) {
        conn = JdbcConnection.getInstance().getConnection();
        String sql = "Insert into Inquiry(post_type, post_title, user_id, user_name, post_content, post_date)"
                + "values (?, ?, ?, ?, ?, now())";

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setString(1, inquiry.getPostType());
            pstmt.setString(2, inquiry.getPostTitle());
            pstmt.setString(3, inquiry.getUserId());
            pstmt.setString(4, inquiry.getUserName());
            pstmt.setString(5, inquiry.getPostContent());
            int row = pstmt.executeUpdate();
            return row;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Inquiry> inquirySelect() {
        ArrayList<Inquiry> inquiries = new ArrayList<>();
        conn = JdbcConnection.getInstance().getConnection();

        String sql = "SELECT * FROM inquiry";

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            while (rs.next()) {
                Inquiry inquiry = new Inquiry();
                inquiry.setPostNum(rs.getInt("post_num"));
                inquiry.setUserId(rs.getString("user_id"));
                inquiry.setPostType(rs.getString("post_type"));
                inquiry.setUserName(rs.getString("user_name"));
                inquiry.setPostTitle(rs.getString("post_title"));
                inquiry.setPostContent(rs.getString("post_content"));
                inquiry.setResponse(rs.getString("response"));
                inquiry.setPostDate(rs.getDate("post_date"));
                inquiries.add(inquiry);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return inquiries;
    }

    public Inquiry inquirySelectOne(int postNum) {
        conn = JdbcConnection.getInstance().getConnection();

        String sql = "SELECT * FROM inquiry where post_num = ?";
        Inquiry inquiry = new Inquiry();

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, postNum);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                inquiry.setPostNum(rs.getInt("post_num"));
                inquiry.setPostType(rs.getString("post_type"));
                inquiry.setPostTitle(rs.getString("post_title"));
                inquiry.setUserId(rs.getString("user_id"));
                inquiry.setUserName(rs.getString("user_name"));
                inquiry.setPostContent(rs.getString("post_content"));
                inquiry.setPostDate(rs.getDate("post_date"));
                inquiry.setResponse(rs.getString("response"));
            }
            return inquiry;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int inquiryDelete(int postNum) {
        conn = JdbcConnection.getInstance().getConnection();

        String sql = "DELETE FROM inquiry where post_num = ?";

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, postNum);
            int row = pstmt.executeUpdate();
            return row;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public int inquiryUpdate(Inquiry inquiry) {
        conn = JdbcConnection.getInstance().getConnection();

        String sql = "UPDATE inquiry SET post_type = ?, post_content = ?, post_title = ?, post_date = now() where post_num = ?";
        try (
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, inquiry.getPostType());
            pstmt.setString(2, inquiry.getPostContent());
            pstmt.setString(3, inquiry.getPostTitle());
            pstmt.setInt(4, inquiry.getPostNum());
            int row = pstmt.executeUpdate();
            return row;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 0;

    }


}
