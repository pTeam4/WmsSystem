package dao;

import config.JdbcConnection;

import java.sql.Connection;

public class StockMovementDao {
    private Connection connection;

    public StockMovementDao() {
        this.connection = JdbcConnection.getInstance().getConnection();
    }
}
