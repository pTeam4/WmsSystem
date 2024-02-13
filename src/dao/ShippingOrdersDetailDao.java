package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import config.JdbcConnection;
import vo.ShippingOrdersDetail;

public class ShippingOrdersDetailDao {
    PreparedStatement pstmt;
    Connection conn;

    public ShippingOrdersDetailDao() {
        this.conn = JdbcConnection.getInstance().getConnection();
    }

    /**
     * 튜플 삽입
     */
    public void addShippingOrdersDetailDao() {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = stringBuilder.append("Insert into Shipping_Orders_Detail () ")
                .append("values ()")
                .toString();
        try {
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            System.out.println();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 상품 번호 검색을 통한 테이블 조회
     */
    public List<ShippingOrdersDetail> getShippingOrdersDetailByProductId(int productId) {
        List<ShippingOrdersDetail> shippingOrdersDetailList = new ArrayList<>();
        ShippingOrdersDetail shippingOrdersDetail = new ShippingOrdersDetail();
        StringBuilder stringBuilder = new StringBuilder();
        String sql = stringBuilder.append("select * from Shipping_Orders_Detail ")
                .append("where product_id = ?")
                .toString();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                shippingOrdersDetail.setId(rs.getInt("id"));
                shippingOrdersDetail.setShippingOrdersId(rs.getInt("shipping_orders_id"));
                shippingOrdersDetail.setProductId(rs.getInt("product_id"));
                shippingOrdersDetail.setQuantity(rs.getInt("quantity"));
                shippingOrdersDetailList.add(shippingOrdersDetail);
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shippingOrdersDetailList;
    }

    public void requestRetrieval(ShippingOrdersDetail shippingOrdersDetail) {
        StringBuilder stringBuilder = new StringBuilder();
        int shippingOrderId = 0;
        try {
            String sql = stringBuilder.append("Insert into Shipping_Orders_Detail(customer_id, quantity) ")
                    .append("values (?,?) ")
                    .append("where shipping_order_id = ?")
                    .toString();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, shippingOrdersDetail.getProductId());
            pstmt.setInt(2, shippingOrdersDetail.getQuantity());
            pstmt.setInt(3, shippingOrdersDetail.getShippingOrdersId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
