package dao;

import config.JdbcConnection;
import vo.Inquiry;
import vo.Notice;

import java.sql.*;
import java.util.ArrayList;

//공지사항
public class NoticeDao {
    Connection conn;
    public int noticeInsert(Notice notice) {
        conn = JdbcConnection.getInstance().getConnection();
        String sql = "Insert into Notice(user_ID, type, title, content, notice_date)"
                + "values (?, ?, ?, ?, now())";

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setString(1, notice.getUserId());
            pstmt.setString(2, notice.getType());
            pstmt.setString(3, notice.getTitle());
            pstmt.setString(4, notice.getContent());
            int row = pstmt.executeUpdate();
            return row;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Notice> noticeSelect() {
        ArrayList<Notice> noticeList = new ArrayList<>();
        conn = JdbcConnection.getInstance().getConnection();

        String sql = "SELECT * FROM notice";

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            while (rs.next()) {
                Notice notice = new Notice();
                notice.setNo(rs.getInt("no"));
                notice.setType(rs.getString("type"));
                notice.setTitle(rs.getString("title"));
                notice.setContent(rs.getString("content"));
                notice.setUserId(rs.getString("user_id"));
                notice.setNoticeDate(rs.getDate("notice_date"));
                noticeList.add(notice);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return noticeList;
    }

    public Notice noticeSelectOne(int no) {
        conn = JdbcConnection.getInstance().getConnection();

        String sql = "SELECT * FROM notice where no = ?";
        Notice notice = new Notice();

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, no);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                notice.setNo(rs.getInt("no"));
                notice.setType(rs.getString("type"));
                notice.setTitle(rs.getString("title"));
                notice.setContent(rs.getString("content"));
                notice.setUserId(rs.getString("user_id"));
                notice.setNoticeDate(rs.getDate("notice_date"));
            }
            return notice;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int noticeDelete(int no) {
        conn = JdbcConnection.getInstance().getConnection();

        String sql = "DELETE FROM notice where no = ?";

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, no);
            int row = pstmt.executeUpdate();
            return row;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public int noticeUpdate(Notice notice) {
        conn = JdbcConnection.getInstance().getConnection();

        String sql = "UPDATE NOTICE SET type = ?, content = ?, title = ?, notice_date = now() where no = ?";
        try (
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, notice.getType());
            pstmt.setString(2, notice.getContent());
            pstmt.setString(3, notice.getTitle());
            pstmt.setInt(4, notice.getNo());
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
