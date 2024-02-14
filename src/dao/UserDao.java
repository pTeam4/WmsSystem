package dao;

import config.GetTexts;
import config.JdbcConnection;
import dto.UserPermission;
import util.UserManager;
import vo.User;

import javax.print.DocFlavor;
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

    //    public List<User> userSelect() {
    public List<UserPermission> userSelect() {
//        String sql = "SELECT * FROM user";
        String sql = "select u.id, name, birth, password, email, telephone, level, state " +
                "from user u join permission p join status s " +
                "on u.permission_id = p.id and u.status_id = s.id";
//        List<User> users = new ArrayList<>();
        List<UserPermission> users = new ArrayList<>();

        try (
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
//                User user = new User();
//                user.setId(resultSet.getString("id"));
//                user.setName(resultSet.getString("name"));
//                user.setBirth(resultSet.getDate("birth"));
//                user.setPw(resultSet.getString("password"));
//                user.setEmail(resultSet.getString("email"));
//                user.setTel(resultSet.getString("telephone"));
//                user.setPermission(resultSet.getInt("permission_id"));
//                user.setStatus(resultSet.getInt("status_id"));
//                users.add(user);
                UserPermission user = new UserPermission();
                user.setId(resultSet.getString("id"));
                user.setName(resultSet.getString("name"));
                user.setBirth(resultSet.getDate("birth"));
                user.setPw(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setTel(resultSet.getString("telephone"));
                user.setLevel(resultSet.getString("level"));
                user.setState(resultSet.getString("state"));
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

    public void userConfirm(String id) {
        try {
            String sql = "update user set permission_id = 2, status_id = 1 where id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void userUpdate(User user, String id) {
        try {
            String sql = "update user set name = ?, birth = ?, password = ?, email = ?, telephone = ? where id = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getName());
            java.sql.Date sqlDate = new java.sql.Date(user.getBirth().getTime());
            pstmt.setDate(2, sqlDate);
            pstmt.setString(3, user.getPw());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getTel());
            pstmt.setString(6, id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String userSelectByNameAndEmail(String name, String email) {
        String id = null;
        try {
            String sql = "select id from user where name = ? and email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getString("id");
            }
            pstmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String userSelectByIdAndName(String id, String name) {
        String pw = null;
        try {
            String sql = "select password from user where id = ? and email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                pw = rs.getString("password");
            }
            pstmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pw;
    }
}
