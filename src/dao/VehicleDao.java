package dao;

import config.JdbcConnection;
import vo.ShippingOrders;
import vo.Vehicle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//차량
public class VehicleDao {
    Connection conn;
    private PreparedStatement pstmt;

    public VehicleDao () {
        this.conn = JdbcConnection.getInstance().getConnection();
    }


    public List<Vehicle> getVehicleList() {
        List<Vehicle> vehicleList = new ArrayList<>();
        Vehicle vehicle = new Vehicle();
        StringBuilder stringBuilder = new StringBuilder();
        String sql = stringBuilder.append("select * from Vehicle")
                .toString();
        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.printf(
                        "%-6s%-12s%-10s%-25s%-16s%-16s%n",
                        rs.getString("num"),
                        rs.getInt("delivery_driver_id"),
                        rs.getString("type"),
                        rs.getInt("contact1"),
                        rs.getString("contact2"),
                        rs.getInt("status")
                );
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicleList;
    }

    public void cancelVehicleDispatchingByWaybillId(int shippingOrdersId) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String sql = stringBuilder.append("UPDATE Vehicle SET ")
                    .append("status = 0 ")
                    .append("where shipping_orders_id = ?")
                    .toString();
            //PreparedStatement 얻기 및 값 지정
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, shippingOrdersId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifyVehicleDispatchingByWaybillId(int shippingOrdersId, String vehicleNum) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String sql = stringBuilder.append("UPDATE Vehicle SET ")
                    .append("num = ?")
                    .append("where shipping_orders_id = ?")
                    .toString();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, vehicleNum);
            pstmt.setInt(2, shippingOrdersId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addVehicleDispatching(String vehicleNum) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String sql = stringBuilder.append("UPDATE Vehicle SET ")
                    .append("status = 1")
                    .append("where num = ?")
                    .toString();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, vehicleNum);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
