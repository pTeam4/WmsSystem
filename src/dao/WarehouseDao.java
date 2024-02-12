package dao;

import config.JdbcConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;

//창고
public class WarehouseDao {
    BufferedReader bufferedReader;
    Connection connection;

    public WarehouseDao() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        this. connection = JdbcConnection.getInstance().getConnection();
    }

    private void warehouseInsert() {

    }
}
