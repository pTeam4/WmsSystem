package dao;

import util.JdbcConnection;
import vo.Waybill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//운송장
public class WaybillDao {
    Connection conn;
    PreparedStatement pstmt;

    public WaybillDao() {
        this.conn = JdbcConnection.getInstance().getConnection();
    }

    public List<Waybill> getWaybillList() {
        List<Waybill> waybillList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        String sql = stringBuilder.append("select * from Waybill")
                .toString();
        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Waybill waybill = new Waybill();
                waybill.setId(rs.getInt("id"));
                waybill.setShippingOrdersId(rs.getInt("shipping_orders_id"));
                waybill.setVehicleNum(rs.getString("vehicle_num"));
                waybill.setDepartureDate(rs.getDate("departure_date"));
                waybill.setArrivalDate(rs.getDate("arrival_date"));

                waybillList.add(waybill);
            }
            /*pstmt.close();*/
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return waybillList;
    }

    public Waybill getWaybillByShippingOrdersId(int shippingOrdersId) {
        Waybill waybill = new Waybill();
        StringBuilder stringBuilder = new StringBuilder();
        String sql = stringBuilder.append("select * from Waybill ")
                .append("where shipping_orders_id = ?")
                .toString();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, shippingOrdersId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                waybill.setId(rs.getInt("id"));
                waybill.setShippingOrdersId(rs.getInt("shipping_orders_id"));
                waybill.setVehicleNum(rs.getString("vehicle_num"));
                waybill.setDepartureDate(rs.getDate("departure_date"));
                waybill.setArrivalDate(rs.getDate("arrival_date"));
            }
            /*pstmt.close();*/
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return waybill;
    }

    public void addWaybill(int shippingOrdersId, String vehicleNum, Date departureDate) {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = stringBuilder.append("Insert into Waybill(shipping_orders_id, vehicle_num, departure_date) ")
                .append("values (?,?,?)")
                .toString();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, shippingOrdersId);
            pstmt.setString(2, vehicleNum);
            pstmt.setDate(3, departureDate);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifyWaybill(Waybill waybill) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String sql = stringBuilder.append("UPDATE Waybill SET ")
                    .append("shipping_orders_id = ? , ")
                    .append("vehicle_num = ? , ")
                    .append("departure_date = ? ")
                    .append("where id = ?")
                    .toString();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, waybill.getShippingOrdersId());
            pstmt.setString(2, waybill.getVehicleNum());
            java.sql.Date departureDate = new java.sql.Date(waybill.getDepartureDate().getTime());
            pstmt.setDate(3, departureDate);
            pstmt.setInt(4, waybill.getId());
            pstmt.executeUpdate();
            /*pstmt.close();*/
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
