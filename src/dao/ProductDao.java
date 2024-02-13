package dao;

import config.JdbcConnection;

import java.sql.Connection;

//상품
public class ProductDao {
    private Connection connection;

    public ProductDao() {
        this.connection = JdbcConnection.getInstance().getConnection();
    }
}
