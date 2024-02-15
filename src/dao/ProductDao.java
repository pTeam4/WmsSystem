package dao;

import config.JdbcConnection;
import vo.Product;

import java.sql.*;

//상품
public class ProductDao {
    private Connection connection;

    public ProductDao() {
        this.connection = JdbcConnection.getInstance().getConnection();
    }

    public int productInsert(Product product) {
        String sql = "INSERT INTO product (name, brand, type, price, sale_price, quantity)" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        int result = 0;

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getBrand());
            preparedStatement.setString(3, product.getType());
            preparedStatement.setInt(4, product.getPrice());
            preparedStatement.setInt(5, product.getSalePrice());
            preparedStatement.setInt(6, product.getQuantity());

            int row = preparedStatement.executeUpdate();

            if (row == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next()) {
                    result = resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }
    public int productUpdate(int productId, Product product) {
        String sql = "UPDATE Product SET name=?, brand=?, type=?, price=?, sale_price=?, quantity=? WHERE id = ?";

        int updatedRows = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getBrand());
            preparedStatement.setString(3, product.getType());
            preparedStatement.setInt(4, product.getPrice());
            preparedStatement.setInt(5, product.getSalePrice());
            preparedStatement.setInt(6, product.getQuantity());
            preparedStatement.setInt(7, productId);
            updatedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updatedRows;
    }
}
