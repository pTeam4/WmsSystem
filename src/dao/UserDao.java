package dao;

import config.GetTexts;
import config.JdbcConnection;
import util.UserManager;
import vo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        String sql = "Insert into User(id, name, birth, password, email, telephone)" +
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
            e.printStackTrace();
        }

    }

    public List<User> userSelect() {
        String sql = "SELECT * FROM user";
        List<User> users = new ArrayList<>();
        try (
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getString("id"));
                user.setName(resultSet.getString("name"));
                user.setBirth(resultSet.getDate("birth"));
                user.setPw(resultSet.getString("pw"));
                user.setEmail(resultSet.getString("email"));
                user.setTel(resultSet.getString("tel"));
                user.setPermission(resultSet.getInt("permission_id"));
                user.setStatus(resultSet.getInt("status_id"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public User userSelectOne(String id, String pw) {
        String sql = "select * from user where id = ? and password = ?";
        User user = null;

        try (
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, pw);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getString("id"));
                    user.setName(resultSet.getString("name"));
                    java.sql.Date sqlDate = new java.sql.Date(resultSet.getDate("birth").getTime());
                    user.setBirth(sqlDate);
                    user.setPw(resultSet.getString("password"));
                    user.setEmail(resultSet.getString("email"));
                    user.setTel(resultSet.getString("telephone"));
                    user.setPermission(resultSet.getInt("permission_id"));
                    user.setStatus(resultSet.getInt("status_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
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
