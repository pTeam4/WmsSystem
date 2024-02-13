package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

//운송장
public class WaybillDao {
    Connection conn;
    PreparedStatement pstmt;

    public void getWaybillList() {


    }

    public void getWaybillByShippingOrdersId() {

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
}
