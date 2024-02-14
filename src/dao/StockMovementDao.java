package dao;

import config.JdbcConnection;
import vo.StockMovement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public void printAllStockMovements() { //입고 요청 전체 출력 메서드
        String sql = "SELECT * FROM Stock_Movement";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            System.out.println("입고 요청 리스트:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int productId = resultSet.getInt("product_id");
                String userId = resultSet.getString("user_id");
                String statusCode = resultSet.getString("status_code");
                System.out.println("ID: " + id + ", Product ID: " + productId + ", User ID: " + userId + ", Status: " + statusCode);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 입고 요청의 상태를 변경하는 메서드
    public int updateStockMovementStatus(int requestId, String statusCode) {
        String sql = "UPDATE Stock_Movement SET status_code = ? WHERE id = ?";
        int updatedRows = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, statusCode);
            preparedStatement.setInt(2, requestId);
            updatedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updatedRows;
    }

    public List<StockMovement> stockSelectByStatus(String statusCode) {
        String sql = "SELECT * FROM stock_movement WHERE status_code = ?";
        List<StockMovement> stockMovements = new ArrayList<>();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setString(1, statusCode);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery();
            ) {

                while (resultSet.next()) {
                    StockMovement stockMovement = new StockMovement();

                    stockMovement.setId(resultSet.getInt("id"));
                    stockMovement.setProductId(resultSet.getInt("product_id"));
                    stockMovement.setUserId(resultSet.getString("user_id"));
                    stockMovement.setStatusCode(resultSet.getString("status_code"));
                    stockMovement.setRequestDatetime(resultSet.getTimestamp("request_datetime"));

                    stockMovements.add(stockMovement);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stockMovements;
    }
}
