package dao;

import util.JdbcConnection;
import dto.StockInfo;
import vo.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//재고
public class StockDao {
    private Connection connection;

    public StockDao() {
        this.connection = JdbcConnection.getInstance().getConnection();
    }

    public List<Stock> stockSelect() {
        String sql = "SELECT * FROM stock";
        List<Stock> stockList = new ArrayList<>();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {

            while (resultSet.next()) {
                Stock stock = new Stock();

                stock.setId(resultSet.getInt("id"));
                stock.setWarehouseId(resultSet.getInt("warehouse_id"));
                stock.setProductId(resultSet.getInt("product_id"));
                stock.setQuantity(resultSet.getInt("quantity"));

                stockList.add(stock);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stockList;
    }

    public List<StockInfo> stockSelectInfo(int warehouseId) {
        String sql = "SELECT s.id, p.name, s.quantity, w.name, w.location" +
                " FROM stock s" +
                " JOIN product p" +
                "   ON s.product_id = p.id" +
                " JOIN warehouse w" +
                "   ON s.warehouse_id = w.id" +
                " WHERE w.id = ?";

        List<StockInfo> stockInfoList = new ArrayList<>();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(1, warehouseId);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {

                while (resultSet.next()) {
                    StockInfo stockInfo = new StockInfo();

                    stockInfo.setStockId(resultSet.getInt("s.id"));
                    stockInfo.setProductName(resultSet.getString("p.name"));
                    stockInfo.setStockQuantity(resultSet.getInt("s.quantity"));
                    stockInfo.setWarehouseName(resultSet.getString("w.name"));
                    stockInfo.setWarehouseLocation(resultSet.getString("w.location"));

                    stockInfoList.add(stockInfo);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stockInfoList;
    }

    public List<Stock> stockSelectOne(int productId) {
        String sql = "SELECT id, warehouse_Id, product_Id, quantity FROM stock WHERE product_Id = ? ";
        List<Stock> stocks = new ArrayList<>();

        try (
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, productId); // PreparedStatement에 매개변수 설정

            try (
                    ResultSet resultSet = statement.executeQuery()
            ) {

                while (resultSet.next()) {
                    Stock stock = new Stock();
                    stock.setId(resultSet.getInt("id"));
                    stock.setProductId(productId);
                    stock.setWarehouseId(resultSet.getInt("warehouse_Id"));
                    stock.setQuantity(resultSet.getInt("quantity"));
                    stocks.add(stock);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stocks;
    }
}
