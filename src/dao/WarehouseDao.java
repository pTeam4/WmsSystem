package dao;

import config.JdbcConnection;
import vo.Warehouse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//창고
public class WarehouseDao {
    Connection connection;

    public WarehouseDao() {
        this.connection = JdbcConnection.getInstance().getConnection();
    }

    public void warehouseInsert(Warehouse warehouse) {
        String sql = "INSERT INTO warehouse (name, location, type)" +
                "VALUES (?, ?, ?)";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setString(1, warehouse.getName());
            preparedStatement.setString(2, warehouse.getLocation());
            preparedStatement.setString(3, warehouse.getType());

            int row = preparedStatement.executeUpdate();

            System.out.printf("창고 %d개가 등록되었습니다.%n", row);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Warehouse> warehouseSelect() {
        String sql = "SELECT * FROM warehouse";
        List<Warehouse> warehouses = new ArrayList<>();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {


            while (resultSet.next()) {
                Warehouse warehouse = new Warehouse();
                warehouse.setId(resultSet.getInt("id"));
                warehouse.setName(resultSet.getString("name"));
                warehouse.setLocation(resultSet.getString("location"));
                warehouse.setType(resultSet.getString("type"));

                warehouses.add(warehouse);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return warehouses;
    }
}
