package service;

import config.GetTexts;
import dao.ExpenseDao;
import dao.SalesDao;
import vo.Expense;
import vo.Sales;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FinanceServiceImpl implements FinanceService {
    ExpenseDao expenseDao = new ExpenseDao();
    SalesDao salesDao = new SalesDao();
    @Override
    public void getExpenseRecords() {
        System.out.print("지출 내역을 조회하고 싶은 창고번호를 입력하세요.");
        int warehouseNo = Integer.parseInt(GetTexts.getInstance().readLine());
        List<Expense> expenses = expenseDao.expenseSelect(warehouseNo);
        System.out.printf("%-4s%-8s%-20s%-12s%-20s\n", "id", "창고번호", "종류", "비용", "지출일자");
        for (Expense expense : expenses) {
            int id = expense.getId();
            String type = expense.getType();
            int cost = expense.getCost();
            Date expenseDate = expense.getExpenseDate();
            System.out.printf("%-4s%-8s%-20s%-12s%-20s\n", id, warehouseNo, type, cost, expenseDate);
        }

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
        System.out.print("지출 일자를 yyyy-MM-dd 형식으로 입력하세요: ");
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

        System.out.print("수정할 지출 유형을 선택하세요 ");
        String type = GetTexts.getInstance().readLine();
        if (!type.isEmpty()) {
            expense.setType(type);
        }

        System.out.print("수정할 지출 비용을 입력하세요 ");
        String costInput = GetTexts.getInstance().readLine();
        if (!costInput.isEmpty()) {
            int cost = Integer.parseInt(costInput);
            expense.setCost(cost);
        }

        System.out.print("수정할 지출 일자를 입력하세요 yyyy-MM-dd (이전 일자: " + expense.getExpenseDate() + "): ");
        String dateString = GetTexts.getInstance().readLine();
        if (!dateString.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate = dateFormat.parse(dateString);
                expense.setExpenseDate(new java.sql.Date(parsedDate.getTime()));
            } catch (ParseException e) {
                System.out.println("잘못된 형식입니다.");
            }
        }

        int rowsAffected = expenseDao.expenseUpdate(expense);
        if (rowsAffected > 0) {
            System.out.println("지출 내역이 성공적으로 수정되었습니다.");
        } else {
            System.out.println("지출 내역 수정에 실패했습니다.");
        }
    }



    @Override
    public void removeExpenseRecord() {
        System.out.print("삭제하고싶은 지출내역번호를 입력하세요. 취소하고싶다면 0입력 ");
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
        ArrayList<Sales> salesArrayList = salesDao.salesSelect(warehouseNo);
        System.out.printf("%-4s%-8s%-20s%-12s%-20s\n", "id", "창고번호", "종류", "매출금액", "매출일자");
        for (Sales sales : salesArrayList)
        {
            int id = sales.getId();
            String type = sales.getType();
            int amount = sales.getAmount();
            Date salesDate = sales.getSalesDate();
            System.out.printf("%-4s%-8s%-20s%-12s%-20s\n", id, warehouseNo, type, amount, salesDate);
        }
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
