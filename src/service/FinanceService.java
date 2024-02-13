package service;
//재무 관리
public interface FinanceService {
    public void getExpenseRecords();

    public void getYearlyExpenseRecords();

    public void getExpenseGraph();

    public void addExpenseRecord();

    public void modifyExpenseRecord();

    public void removeExpenseRecord();

    void getSalesRecords();

    void getSalesGraph();

    public void getTotalSettlementRecords();
}
