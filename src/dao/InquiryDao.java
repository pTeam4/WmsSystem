package dao;

import config.JdbcConnection;
import vo.Inquiry;

import java.sql.*;
import java.util.ArrayList;

//문의글
public class InquiryDao {
    Connection conn;

    /**
     * 문의게시판 테이블에 튜플을 추가한다.
     * @param inquiry
     * @return 추가에 성공하면 추가한 행의 수를 반환하고 실패했을경우 0을 반환한다.
     */
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

    /**
     * 문의 게시판 테이블을 전체 조회한다.
     * @return 전체 조회한 튜플들을 ArrayList에 담아 반환한다.
     */
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

    /**
     * 문의 게시판 테이블 튜플 하나를 상세 조회한다.
     * @param postNum 게시물 번호를 매개변수로 받는다.
     * @return 조회한 Inquiry 객체를 반환한다.
     */
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

    /**
     * 문의 게시판 테이블의 튜플 하나를 삭제한다.
     * @param postNum 게시물 번호를 매개변수로 받는다.
     * @return 삭제에 성공하면 삭제에 성공한 행의 수를 리턴 아닌경우 0을 리턴한다.
     */
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

    /**
     * 문의 게시판 테이블의 튜플 하나를 업데이트 한다.
     * @param inquiry 업데이트 내용이 담긴 Inquiry 객체를 매개변수로 갖는다.
     * @return 삭제에 성공하면 삭제에 성공한 행의 수를 리턴 아닌경우 0을 리턴한다.
     */
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
