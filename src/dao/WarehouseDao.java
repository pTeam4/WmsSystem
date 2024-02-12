package dao;

import config.JdbcConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//창고
public class WarehouseDao {
    BufferedReader bufferedReader;
    Connection connection;

    public WarehouseDao() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        this.connection = JdbcConnection.getInstance().getConnection();
    }

    private void warehouseInsert() {
        String sql = "INSERT INTO warehouse (name, location, type)" +
                "VALUES (?, ?, ?)";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            System.out.print("창고 이름을 입력하세요: ");
            String name = bufferedReader.readLine();

            System.out.print("창고 위치를 입력하세요: ");
            String location = bufferedReader.readLine();

            System.out.print("창고 종류를 입력하세요: ");
            String type = bufferedReader.readLine();

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, location);
            preparedStatement.setString(3, type);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
