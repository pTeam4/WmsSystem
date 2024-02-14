package dao;

import config.GetTexts;
import config.JdbcConnection;
import config.UserManager;
import vo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    PreparedStatement pstmt = null;
    Connection conn = null;
    GetTexts getTexts = null;
    UserManager userManager;

    public UserDao() {
        this.conn = JdbcConnection.getInstance().getConnection();
        this.getTexts = GetTexts.getInstance();
    }

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
            pstmt.setString(6, user.getTel());
            pstmt.executeUpdate();
            System.out.println("db insert ok");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void userSelect() {
        try {
            User user = new User();
            String sql = "select id, name, birth, pw, email, tel, p.level, s.state " +
                    "from user u join permission p join status s " +
                    "on u.permission_id = p.id and u.status_id = s.id";
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                java.sql.Date sqlDate = new java.sql.Date(rs.getDate("birth").getTime());
                user.setBirth(sqlDate);
                user.setPw(rs.getString("pw"));
                user.setEmail(rs.getString("email"));
                user.setTel(rs.getString("tel"));
                user.setPermission(rs.getInt("permission_id"));
                user.setStatus(rs.getInt("status_id"));
                System.out.println(user.getId() + " 로그인 성공");

                rs.close();
                pstmt.close();
            } else {
                System.out.println("없는 회원입니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void userSelectOne(String id, String pw) {
        try {
            String sql = "select * from user where id = ? and pw = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, pw);
            UserManager userManager = UserManager.getInstance();
            User user = new User();
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                java.sql.Date sqlDate = new java.sql.Date(rs.getDate("birth").getTime());
                user.setBirth(sqlDate);
                user.setPw(rs.getString("pw"));
                user.setEmail(rs.getString("email"));
                user.setTel(rs.getString("tel"));
                user.setPermission(rs.getInt("permission_id"));
                user.setStatus(rs.getInt("status_id"));

                userManager.setCurrentUser(user);
                User currentUser = userManager.getCurrentUser();
                System.out.println(currentUser.getName() + " 로그인 성공");

                rs.close();
                pstmt.close();
            } else {
                System.out.println("없는 회원입니다.");
            }


        } catch (SQLException e) {
            e.getMessage();
        }
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
