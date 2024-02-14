package dao;

import config.JdbcConnection;
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

    public List<Stock> getAllStocks(int productId) {
        String SELECT_STOCK_QUERY = "SELECT id, warehouseId, productId, quantity FROM stock WHERE productId = ? ";
        List<Stock> stocks = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_STOCK_QUERY)){
             ResultSet resultSet = statement.executeQuery();
            statement.setInt(1, productId);

            while (resultSet.next()) {
                Stock stock = new Stock();
                stock.setId(resultSet.getInt("id"));
                stock.setProductId(productId);
                stock.setWarehouseId(resultSet.getInt("warehouseId"));
                stock.setQuantity(resultSet.getInt("quantity"));
                stocks.add(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stocks;
    }
}
