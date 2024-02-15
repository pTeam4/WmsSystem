package dao;

import config.JdbcConnection;
import dto.WarehouseInfo;
import vo.Warehouse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//창고
public class WarehouseDao {
    Connection connection;

    public WarehouseDao() {
        this.connection = JdbcConnection.getInstance().getConnection();
    }

    public int warehouseInsert(Warehouse warehouse) {
        String sql = "INSERT INTO warehouse (name, location, type)" +
                "VALUES (?, ?, ?)";

        int row = 0;

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setString(1, warehouse.getName());
            preparedStatement.setString(2, warehouse.getLocation());
            preparedStatement.setString(3, warehouse.getType());

            row = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return row;
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

    public List<Warehouse> warehouseSelectByLocation(String location) {
        String sql = "SELECT * FROM warehouse WHERE location = ?";
        List<Warehouse> warehouseList = new ArrayList<>();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {

            preparedStatement.setString(1, location);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery();
            ) {

                while (resultSet.next()) {
                    Warehouse warehouse = new Warehouse();
                    warehouse.setId(resultSet.getInt("id"));
                    warehouse.setName(resultSet.getString("name"));
                    warehouse.setType(resultSet.getString("type"));

                    warehouseList.add(warehouse);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return warehouseList;
    }

    public Warehouse warehouseSelectOne(int warehouseId) {
        String sql = "SELECT * FROM warehouse WHERE id = ?";
        Warehouse warehouse = new Warehouse();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {

            preparedStatement.setInt(1, warehouseId);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery();
            ) {

                if (resultSet.next()) {
                    warehouse.setId(resultSet.getInt("id"));
                    warehouse.setName(resultSet.getString("name"));
                    warehouse.setLocation(resultSet.getString("location"));
                    warehouse.setType(resultSet.getString("type"));
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return warehouse;
    }

    public List<WarehouseInfo> warehouseSelectWithStock(int warehouseId) {
        String sql = "SELECT w.id, w.name, w.location, w.type, p.name, s.quantity" +
                " FROM warehouse w" +
                " JOIN stock s" +
                " ON w.id = s.warehouse_id" +
                " JOIN product p" +
                " ON s.product_id = p.id" +
                " WHERE w.id = ?";

        List<WarehouseInfo> warehouseInfoList = new ArrayList<>();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {

            preparedStatement.setInt(1, warehouseId);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery();
            ) {

                while (resultSet.next()) {
                    WarehouseInfo warehouseInfo = new WarehouseInfo();

                    warehouseInfo.setWarehouseId(resultSet.getInt("w.id"));
                    warehouseInfo.setWarehouseName(resultSet.getString("w.name"));
                    warehouseInfo.setWarehouseLocation(resultSet.getString("w.location"));
                    warehouseInfo.setWarehouseType(resultSet.getString("w.type"));
                    warehouseInfo.setProductName(resultSet.getString("p.name"));
                    warehouseInfo.setStockQuantity(resultSet.getInt("s.quantity"));

                    warehouseInfoList.add(warehouseInfo);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return warehouseInfoList;
    }
}
