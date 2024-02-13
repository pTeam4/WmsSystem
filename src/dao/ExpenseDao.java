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
                + "values (?, ?, ?, now())";

        try(
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        )
        {
            pstmt.setInt(1, expense.getWarehouseId());
            pstmt.setString(2, expense.getType());
            pstmt.setInt(3, expense.getCost());
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void expenseSelect()
    {
        conn = JdbcConnection.getInstance().getConnection();
        String sql = "select * from Expense";

        try(
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        )
        {
            ResultSet rs = pstmt.executeQuery();
            System.out.printf("%-4s%-8s%-20s%-12s%-20s\n", "id", "창고번호", "종류", "비용", "지출일자");
            while(rs.next())
            {
                int id = rs.getInt("id");
                int warehouseId= rs.getInt("warehouse_id");
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
    public void expenseSelectByYear(String year)
    {
        conn = JdbcConnection.getInstance().getConnection();
        String sql = "select * from Expense where year(expense_date) = ?";

        try(
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        )
        {
            pstmt.setString(1, year);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                int id = rs.getInt("id");
                int warehouseId= rs.getInt("warehouse_id");
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
        String sql = "select * from Expense where id = ?";

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
    public void expenseUpdate(int id)
    {
        conn = JdbcConnection.getInstance().getConnection();
        String sql = "UPDATE expense set warehouse_id = ?, type = ?, cost = ?, expense_date = ? where id = ?";
        System.out.print("수정할 창고 아이디를 입력하세요: ");
        String warehouseIdInput = GetTexts.getInstance().readLine();
    }

    public void expenseSum()
    {

    }
}
