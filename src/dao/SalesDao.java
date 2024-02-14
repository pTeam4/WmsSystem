package dao;

import config.JdbcConnection;
import vo.Sales;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class SalesDao {
    Connection conn;
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
