package dao;

import config.JdbcConnection;

import java.sql.*;
import java.util.Date;

public class SalesDao {
    Connection conn;
    public void salesSelect(int warehouseId)
    {
        conn = JdbcConnection.getInstance().getConnection();
        String sql = "select * from sales where warehouse_id = ?";

        try(
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        )
        {
            pstmt.setInt(1, warehouseId);
            ResultSet rs = pstmt.executeQuery();
            System.out.printf("%-4s%-8s%-20s%-12s%-20s\n", "id", "창고번호", "종류", "매출금액", "매출일자");
            while(rs.next())
            {
                int id = rs.getInt("id");
                String type = rs.getString("type");
                int cost = rs.getInt("amount");
                Date salesDate = rs.getDate("sales_date");
                System.out.printf("%-4s%-8s%-20s%-12s%-20s\n", id, warehouseId, type, cost, salesDate);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        int totalCost = salesSum(warehouseId);
        System.out.println(warehouseId + "번 창고의 총 지출 합계 : " + totalCost);
    }

    private int salesSum(int warehouseId) {
        conn = JdbcConnection.getInstance().getConnection();
        String sql = "select sum(amount) from sales where warehouse_id = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, warehouseId);
            ResultSet rs = pstmt.executeQuery();
            int totalamount = 0;
            if(rs.next())
            {
                totalamount = rs.getInt("sum(amount)");
            }
            return totalamount;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public int SalesSumByYear(int warehouseId, String year)
    {
        conn = JdbcConnection.getInstance().getConnection();
        String sql = "select sum(amount) from sales where warehouse_id = ? and year(sales_date) = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, warehouseId);
            pstmt.setString(2, year);
            ResultSet rs = pstmt.executeQuery();
            int totalamount = 0;
            if(rs.next())
            {
                totalamount = rs.getInt("sum(amount)");
            }
            return totalamount;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }


}
