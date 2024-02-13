package dao;

import config.JdbcConnection;
import vo.StockMovement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StockMovementDao {
    private Connection connection;

    public StockMovementDao() {
        this.connection = JdbcConnection.getInstance().getConnection();
    }

    public int stockMovementInsert(StockMovement stockMovement) {
        String sql = "INSERT INTO Stock_Movement (product_id, user_id, status_code)" +
                "VALUES (?, ?, ?)";

        int row = 0;

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {

            preparedStatement.setInt(1, stockMovement.getProductId());
            preparedStatement.setString(2, stockMovement.getUserId());
            preparedStatement.setString(3, stockMovement.getStatusCode());

            row = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return row;
    }
}
