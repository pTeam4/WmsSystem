package dao;

import config.JdbcConnection;
import dto.StockInfo;

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

    public List<StockInfo> stockSelect() {
        String sql = "SELECT s.id, p.name, s.quantity, w.name, w.location" +
                " FROM stock s" +
                " JOIN product p" +
                "   ON s.product_id = p.id" +
                " JOIN warehouse w" +
                "   ON s.warehouse_id = w.id";

        List<StockInfo> stockInfoList = new ArrayList<>();

        try (
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stockInfoList;
    }
}
