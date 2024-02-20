package dao;

import util.JdbcConnection;
import vo.Sales;

import java.sql.*;
import java.util.ArrayList;

public class SalesDao {
    Connection conn;

    /**
     * 매출내역 테이블을 전체 조회한다.
     * @param warehouseId 매출내역을 조회할 창고번호를 매개변수로 갖는다.
     * @return 조회내역을 ArrayList로 반환한다.
     */
    public ArrayList<Sales> salesSelect(int warehouseId)
    {
        ArrayList<Sales> salesList = new ArrayList<>();
        conn = JdbcConnection.getInstance().getConnection();
        String sql = "select * from sales where warehouse_id = ?";

        try(
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        )
        {
            pstmt.setInt(1, warehouseId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                Sales sales = new Sales();
                int id = rs.getInt("id");
                String type = rs.getString("type");
                int amount = rs.getInt("amount");
                Date salesDate = rs.getDate("sales_date");
                sales.setId(id);
                sales.setType(type);
                sales.setAmount(amount);
                sales.setWarehouseId(warehouseId);
                sales.setSalesDate(salesDate);
                salesList.add(sales);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return salesList;
    }

    /**
     * 해당 년도의 총 매출 내역을 조회한다
     * @param warehouseId 총 매출내역을 조회할 창고번호를 매개변수로 가진다.
     * @return 총 매출내역을 반환한다.
     */
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
