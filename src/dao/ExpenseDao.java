package dao;

import config.GetTexts;
import config.JdbcConnection;
import vo.Expense;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//지출 Dao
public class ExpenseDao {
    Connection conn;
    public void expenseInsert(Expense expense)
    {
        conn = JdbcConnection.getInstance().getConnection();
        String sql = "Insert into Expense(warehouse_id, type, cost, expense_date) "
                + "values (?, ?, ?, ?)";

        try(
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        )
        {
            pstmt.setInt(1, expense.getWarehouseId());
            pstmt.setString(2, expense.getType());
            pstmt.setInt(3, expense.getCost());
            pstmt.setDate(4, (java.sql.Date) expense.getExpenseDate());
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void expenseSelect(int warehouseId)
    {
        conn = JdbcConnection.getInstance().getConnection();
        String sql = "select * from Expense where ?";

        try(
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        )
        {
            pstmt.setInt(1, warehouseId);
            ResultSet rs = pstmt.executeQuery();
            System.out.printf("%-4s%-8s%-20s%-12s%-20s\n", "id", "창고번호", "종류", "비용", "지출일자");
            while(rs.next())
            {
                int id = rs.getInt("id");
                String type = rs.getString("type");
                int cost = rs.getInt("cost");
                Date expenseDate = rs.getDate("expense_date");
                System.out.printf("%-4s%-8s%-20s%-12s%-20s\n", id, warehouseId, type, cost, expenseDate);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        int totalCost = expenseSum(warehouseId);
        System.out.println(warehouseId + "번 창고의 총 지출 합계 : " + totalCost);
    }

    public Expense expenseSelectOne(int id)
    {
        Expense expense = new Expense();
        conn = JdbcConnection.getInstance().getConnection();
        String sql = "select * from Expense where id = ?";

        try(
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        )
        {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            System.out.printf("%-4s%-8s%-20s%-12s%-20s\n", "id", "창고번호", "종류", "비용", "지출일자");
            if(rs.next())
            {
                int expenseId = rs.getInt("id");
                int warehouseId= rs.getInt("warehouse_id");
                String type = rs.getString("type");
                int cost = rs.getInt("cost");
                Date expenseDate = rs.getDate("expense_date");
                expense.setId(expenseId);
                System.out.printf("%-4s%-8s%-20s%-12s%-20s\n", id, warehouseId, type, cost, expenseDate);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return expense;
    }
    public void expenseSelectByYear(String year, int warehouseId)
    {
        conn = JdbcConnection.getInstance().getConnection();
        String sql = "select * from Expense where year(expense_date) = ? and warehouse_id = ?";

        try(
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        )
        {
            pstmt.setString(1, year);
            pstmt.setInt(2, warehouseId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                int id = rs.getInt("id");
                String type = rs.getString("type");
                int cost = rs.getInt("cost");
                Date expenseDate = rs.getDate("expense_date");
                System.out.printf("%-4s%-8s%-20s%-12s%-20s\n", id, warehouseId, type, cost, expenseDate);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void expenseDelete(int id)
    {
        conn = JdbcConnection.getInstance().getConnection();
        String sql = "delete from Expense where id = ?";

        try(
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        )
        {
            pstmt.setInt(1, id);
            int n = pstmt.executeUpdate();
            if(n == 1)
            {
                System.out.println(id + "번 지출 내역를 삭제하였습니다.");
            }
            else {
                System.out.println("삭제 실패했습니다. 해당 번호의 지출 내역이 있는지 확인해주세요.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void expenseUpdate(Expense expense) {
        conn = JdbcConnection.getInstance().getConnection();
        String sql = "UPDATE expense set ";
    }




    public int expenseSum(int warehouseId)
    {
        conn = JdbcConnection.getInstance().getConnection();
        String sql = "select sum(cost) from expense where warehouse_id = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, warehouseId);
            ResultSet rs = pstmt.executeQuery();
            int totalcost = 0;
            if(rs.next())
            {
                totalcost = rs.getInt("sum(cost)");
            }
            return totalcost;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public int expenseSumByYear(int warehouseId, String year)
    {
        conn = JdbcConnection.getInstance().getConnection();
        String sql = "select sum(cost) from expense where warehouse_id = ? and year(expense_date) = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, warehouseId);
            pstmt.setString(2, year);
            ResultSet rs = pstmt.executeQuery();
            int totalcost = 0;
            if(rs.next())
            {
                totalcost = rs.getInt("sum(cost)");
            }
            return totalcost;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
}
