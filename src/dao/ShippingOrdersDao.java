package dao;

import util.JdbcConnection;
import vo.ShippingOrders;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShippingOrdersDao {
    PreparedStatement pstmt;
    Connection conn;

    public List<ShippingOrders> getShippingOrdersList() {
        conn = JdbcConnection.getInstance().getConnection();
        List<ShippingOrders> shippingOrdersList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        String sql = stringBuilder.append("select * from Shipping_Orders ")
                .append("where approved_status = 1")
                .toString();
        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ShippingOrders shippingOrders = new ShippingOrders();
                shippingOrders.setId(rs.getInt("id"));
                shippingOrders.setUserId(rs.getString("user_id"));
                shippingOrders.setDeliveryAddress(rs.getString("delivery_address"));
                shippingOrders.setOrderDate(rs.getDate("order_date"));
                shippingOrders.setDeliveryDate(rs.getDate("delivery_date"));
                shippingOrders.setStatus(rs.getInt("status"));
                shippingOrders.setApprovedStatus(rs.getInt("approved_status"));

                shippingOrdersList.add(shippingOrders);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shippingOrdersList;
    }

    public int requestRetrieval(ShippingOrders shippingOrders) {
        conn = JdbcConnection.getInstance().getConnection();
        int shippingOrdersId = 0;
        StringBuilder stringBuilder = new StringBuilder();
        String sql = stringBuilder.append("Insert into Shipping_Orders(user_id, delivery_address, order_date, delivery_date) ")
                .append("values (?,?,?,?)")
                .toString();
        try {
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, shippingOrders.getUserId());
            pstmt.setString(2, shippingOrders.getDeliveryAddress());
            java.sql.Date orderDate = new java.sql.Date(shippingOrders.getOrderDate().getTime());
            pstmt.setDate(3, orderDate);
            java.sql.Date deliveryDate = new java.sql.Date(shippingOrders.getDeliveryDate().getTime());
            pstmt.setDate(4, deliveryDate);
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                shippingOrdersId = generatedKeys.getInt(1); // shipping_orders_id 값
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shippingOrdersId;
    }

    public void approveRetrievalRequest(int shippingOrdersId) {
        conn = JdbcConnection.getInstance().getConnection();
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String sql = stringBuilder.append("UPDATE Shipping_Orders SET ")
                    .append("approved_status = 1 ")
                    .append("where id = ?")
                    .toString();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, shippingOrdersId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 출고 승인을 기다리고 있는 목록 리스트
     */
    public List<ShippingOrders> getAwaitedShippingOrdersList() {
        conn = JdbcConnection.getInstance().getConnection();
        List<ShippingOrders> shippingOrdersList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        String sql = stringBuilder.append("select * from Shipping_Orders ")
                .append("where approved_status = 0")
                .toString();
        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ShippingOrders shippingOrders = new ShippingOrders();
                shippingOrders.setId(rs.getInt("id"));
                shippingOrders.setUserId(rs.getString("user_id"));
                shippingOrders.setDeliveryAddress(rs.getString("delivery_address"));
                shippingOrders.setOrderDate(rs.getDate("order_date"));
                shippingOrders.setDeliveryDate(rs.getDate("delivery_date"));
                shippingOrders.setStatus(rs.getInt("status"));
                shippingOrders.setStatus(rs.getInt("approved_status"));

                shippingOrdersList.add(shippingOrders);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shippingOrdersList;
    }

    public ShippingOrders getShippingOrder(int shippingOrdersId) {
        ShippingOrders shippingOrders = new ShippingOrders();
        conn = JdbcConnection.getInstance().getConnection();

        StringBuilder stringBuilder = new StringBuilder();
        String sql = stringBuilder.append("select * from Shipping_Orders ")
                .append("where id = ?")
                .toString();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, shippingOrdersId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                shippingOrders.setId(rs.getInt("id"));
                shippingOrders.setUserId(rs.getString("user_id"));
                shippingOrders.setDeliveryAddress(rs.getString("delivery_address"));
                shippingOrders.setOrderDate(rs.getDate("order_date"));
                shippingOrders.setDeliveryDate(rs.getDate("delivery_date"));
                shippingOrders.setStatus(rs.getInt("status"));
                shippingOrders.setApprovedStatus(rs.getInt("approved_status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shippingOrders;
    }
}
