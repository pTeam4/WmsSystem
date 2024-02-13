package service;

import config.GetTexts;
import dao.ExpenseDao;
import dao.SalesDao;
import vo.Expense;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FinanceServiceImpl implements FinanceService {
    ExpenseDao expenseDao = new ExpenseDao();
    SalesDao salesDao = new SalesDao();
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
        System.out.print("수정할 지출 일자를 yyyy-MM-dd 형식으로 입력하세요: ");
        String dateString = GetTexts.getInstance().readLine();
        java.sql.Date expenseDate = null;
        if (!dateString.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                java.util.Date parsedDate = dateFormat.parse(dateString);
                expenseDate = new java.sql.Date(parsedDate.getTime());
            } catch (ParseException e) {
                System.out.println("잘못 입력하셨습니다.");
            }
        }
        expense.setWarehouseId(warehouseId);
        expense.setType(type);
        expense.setCost(cost);
        expense.setExpenseDate(expenseDate);
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
        expenseDao.expenseUpdate(expense);
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
    public void getSalesRecords() {
        System.out.print("매출 내역을 조회하고 싶은 창고번호를 입력하세요.");
        int warehouseNo = Integer.parseInt(GetTexts.getInstance().readLine());
        salesDao.salesSelect(warehouseNo);
    }

    @Override
    public void getSalesGraph() {

    }

    @Override
    public void getTotalSettlementRecords() {
        System.out.print("총 정산내역을 조회할 창고번호를 입력하세요.");
        int warehouseNo = Integer.parseInt(GetTexts.getInstance().readLine());
        System.out.print("총 정산내역을 조회할 년도를 입력하세요.");
        String year = GetTexts.getInstance().readLine();
        int totalAmount = salesDao.SalesSumByYear(warehouseNo,year);
        int totalCost = expenseDao.expenseSumByYear(warehouseNo, year);
        String lastYear = String.valueOf(Integer.parseInt(year) - 1);
        int lastYearTotalAmount = salesDao.SalesSumByYear(warehouseNo, lastYear);
        int lastYearTotalCost = expenseDao.expenseSumByYear(warehouseNo,lastYear);
        int profit = totalAmount-totalCost;
        int lastYearProfit = lastYearTotalAmount - lastYearTotalCost;
        double profitChangeRate = 0.0;
        if (lastYearProfit != 0) {
            profitChangeRate = ((double) (profit - lastYearProfit) / lastYearProfit) * 100;
        }


        System.out.println("당해년도 매출 : " + totalAmount);
        System.out.println("당해년도 지출 : " + totalCost);
        System.out.println("당해년도 순이익 : " + profit);
        System.out.println("당해년도 순이익 증감률(이전년도와 비교): " + profitChangeRate + "%");
    }
}
