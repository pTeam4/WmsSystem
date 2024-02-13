package dao;

import java.sql.*;
import config.JdbcConnection;
import config.GetTexts;
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
    public void getShippingOrdersDetailByProductId(int productId) {
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

    /**
     * 테이블 수정
     */
    public void modifyShippingOrdersDetailDao() {


    }

    /**
     * 튜플 삭제
     */
    public void removeShippingOrdersDetailDao() {


    }

}
