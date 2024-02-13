package dao;

import config.GetTexts;
import config.JdbcConnection;
import vo.User;

import java.io.BufferedReader;
import java.sql.*;

public class UserDao {
    PreparedStatement pstmt = null;
    Connection conn = null;
    GetTexts getTexts = null;

    public UserDao() {
        this.conn = JdbcConnection.getInstance().getConnection();
        this.getTexts = GetTexts.getInstance();
    }

    //    public void userInsert(String name, Date birth, String id, String pw, String email, String tel) {
    public void userInsert(User user) {
//        User user = new User();
        String sql = "Insert into User(id, name, birth, pw, email, tel)" +
                "values(?, ?, ?, ?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            java.sql.Date sqlDate = new java.sql.Date(user.getBirth().getTime());
            pstmt.setDate(3, sqlDate);
            pstmt.setString(4, user.getPw());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getTel()); //nullable
            pstmt.executeUpdate();
            System.out.println("db insert ok");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void userSelect() {

    }

    public User userSelectOne(String id, String pw) {
        try {
            User user = User.getInstance();
            String sql = "select * from user where id = ? and pw = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, pw);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                java.sql.Date sqlDate = new java.sql.Date(rs.getDate("birth").getTime());
                user.setBirth(sqlDate);
                user.setPw(rs.getString("pw"));
                user.setEmail(rs.getString("email"));
                user.setTel(rs.getString("tel"));
                user.setPermission(rs.getString("permission"));
                user.setStatus(rs.getString("status"));
                System.out.println(user.getId() + " 로그인 성공");

                rs.close();
                pstmt.close();
            } else {
                System.out.println("없는 회원입니다.");
            }

            return user;
        } catch (SQLException e) {
            e.getMessage();
        }
        return null;
    }

    public void userDelete() {
    }

    public void userSelectByStatus() {
    }

    public void userConfirm() {
    }

    public void userUpdate() {
    }
}
