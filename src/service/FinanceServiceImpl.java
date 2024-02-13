package service;

import config.GetTexts;
import dao.ExpenseDao;
import vo.Expense;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FinanceServiceImpl implements FinanceService {
    ExpenseDao expenseDao = new ExpenseDao();
    @Override
    public void getExpenseRecords() {
        System.out.print("지출 내역을 조회하고 싶은 창고번호를 입력하세요.");
        int warehouseNo = Integer.parseInt(GetTexts.getInstance().readLine());
        expenseDao.expenseSelect(warehouseNo);
    }

    @Override
    public void getYearlyExpenseRecords() {
        System.out.print("조회하고 싶은 연도를 입력하세요.");
        String year = GetTexts.getInstance().readLine();
        System.out.print("조회하고 싶은 창고번호를 입력하세요.");
        int warehouseId = Integer.parseInt(GetTexts.getInstance().readLine());
        expenseDao.expenseSelectByYear(year, warehouseId);
    }

    @Override
    public void getExpenseGraph() {

    }

    @Override
    public void addExpenseRecord() {
        Expense expense = new Expense();
        System.out.print("창고 아이디를 입력하세요.");
        int warehouseId = Integer.parseInt(GetTexts.getInstance().readLine());
        System.out.print("지출 종류를 입력하세요.");
        String type = GetTexts.getInstance().readLine();
        System.out.print("지출 비용을 입력하세요.");
        int cost = Integer.parseInt(GetTexts.getInstance().readLine());
        expense.setWarehouseId(warehouseId);
        expense.setType(type);
        expense.setCost(cost);
        expenseDao.expenseInsert(expense);
    }

    @Override
    public void modifyExpenseRecord() {
        System.out.print("수정하고 싶은 지출 내역 번호를 입력하세요: ");
        int id = Integer.parseInt(GetTexts.getInstance().readLine());
        Expense expense = expenseDao.expenseSelectOne(id);

        if (expense == null) {
            System.out.println("해당 내역이 존재하지 않습니다.");
            return;
        }
        expenseDao.expenseUpdate(id);
    }


    @Override
    public void removeExpenseRecord() {
        System.out.print("삭제하고싶은 지출내역번호를 입력하세요. 취소하고싶다면 0입력");
        int id = Integer.parseInt(GetTexts.getInstance().readLine());
        if(id == 0)
        {
            System.out.println("삭제 취소되었습니다.");
        }
        else {
            expenseDao.expenseDelete(id);
        }
    }

    @Override
    public void getRevenueRecords() {

    }

    @Override
    public void getRevenueGraph() {

    }

    @Override
    public void getTotalSettlementRecords() {

    }
}
