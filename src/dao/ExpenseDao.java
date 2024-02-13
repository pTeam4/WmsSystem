package dao;

import config.JdbcConnection;
import vo.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public List<Expense> expenseSelect(int warehouseId) {
        conn = JdbcConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Expense WHERE warehouse_id = ?";
        List<Expense> expenseList = new ArrayList<>();

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, warehouseId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String type = rs.getString("type");
                int cost = rs.getInt("cost");
                Date expenseDate = rs.getDate("expense_date");

                Expense expense = new Expense();
                expense.setId(id);
                expense.setType(type);
                expense.setCost(cost);
                expense.setExpenseDate(expenseDate);
                expense.setWarehouseId(warehouseId);
                expenseList.add(expense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenseList;
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
            if(rs.next())
            {
                String type = rs.getString("type");
                int cost = rs.getInt("cost");
                Date expenseDate = rs.getDate("expense_date");
                int warehouseId = rs.getInt("warehouse_id");

                expense.setId(id);
                expense.setType(type);
                expense.setCost(cost);
                expense.setExpenseDate(expenseDate);
                expense.setWarehouseId(warehouseId);
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

    public int expenseDelete(int id)
    {
        conn = JdbcConnection.getInstance().getConnection();
        String sql = "delete from Expense where id = ?";

        try(
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        )
        {
            pstmt.setInt(1, id);
            int n = pstmt.executeUpdate();
            return n;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int expenseUpdate(Expense expense) {
        conn = JdbcConnection.getInstance().getConnection();
        String sql = "UPDATE expense set type = ?, expense_date = ?, cost = ? where id = ?";
        try(
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        )
        {
            pstmt.setString(1, expense.getType());
            pstmt.setDate(2, (java.sql.Date) expense.getExpenseDate());
            pstmt.setInt(3, expense.getCost());
            pstmt.setInt(4, expense.getId());
            int row = pstmt.executeUpdate();
            return row;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
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
