package dao;

import config.JdbcConnection;
import vo.ShippingOrders;
import vo.ShippingOrdersDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShippingOrdersDao {
    PreparedStatement pstmt;
    Connection conn;

    public ShippingOrdersDao() {
        this.conn = JdbcConnection.getInstance().getConnection();
    }
    public void getShippingOrdersList() {
        ShippingOrders shippingOrders = new ShippingOrders();
        StringBuilder stringBuilder = new StringBuilder();
        String sql = stringBuilder.append("select * from Shipping_Orders")
                .toString();
        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.printf(
                        "%-6s%-12s%-10s%-25s%-16s%-16s%n",
                        rs.getInt("id"),
                        rs.getInt("shipping_orders_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity")
                );
            }
            System.out.println("-----------------------------------");
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
