package dao;

import config.GetTexts;
import config.JdbcConnection;
import vo.Waybill;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

//운송장
public class WaybillDao {
    Connection conn;
    PreparedStatement pstmt;

    public WaybillDao () {
        this.conn = JdbcConnection.getInstance().getConnection();
    }

    public List<Waybill> getWaybillList() {
        Waybill waybill = new Waybill();
        List<Waybill> waybillList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        String sql = stringBuilder.append("select * from Waybill")
                .toString();
        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                waybill.setId(rs.getInt("id"));
                waybill.setShippingOrdersId(rs.getInt("shipping_orders_id"));
                waybill.setVehicleNum(rs.getString("vehicle_num"));
                waybill.setDepartureDate(rs.getDate("departure_date"));
                waybill.setArrivalDate(rs.getDate("arrival_date"));

                waybillList.add(waybill);
            }
            pstmt.close();
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
            pstmt.close();
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
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, shippingOrdersId);
            pstmt.setString(2, vehicleNum);
            pstmt.setDate(3, departureDate);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifyWaybill(int waybillId) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String sql = stringBuilder.append("UPDATE Waybill SET ")
                    .append("shipping_orders_id = ?")
                    .append("vehicle_num = ?")
                    .append("departure_date = ?")
                    .append("where waybill_id = ?")
                    .toString();
            pstmt = conn.prepareStatement(sql);
            int shippingOrdersId = Integer.parseInt(GetTexts.getInstance().readLine());
            pstmt.setInt(1, shippingOrdersId);
            String vehicleNum = GetTexts.getInstance().readLine();
            pstmt.setString(2, vehicleNum);
            try {
                String dateInput = GetTexts.getInstance().readLine();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = simpleDateFormat.parse(dateInput);
                java.sql.Date departureDate = new java.sql.Date(utilDate.getTime());
                pstmt.setDate(3, departureDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            pstmt.setInt(4, waybillId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
