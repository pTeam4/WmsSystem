package view;

import service.FinanceService;
import service.FinanceServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//재무 관리 메뉴
public class FinanceServiceMenu {
    public static void financeServiceMenu()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        FinanceService financeService = new FinanceServiceImpl();
        int menuno = 0;
        System.out.println("1. 지출내역 조회 2. 연간 지출내역 조회 3. 지출내역 등록 4. 지출내역 수정 5. 지출내역 삭제 6. 매출내역 조회 7. 총 정산 내역 조회 8. 이전메뉴로 돌아가기");
        try {
            menuno = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            System.out.println("잘못 입력하셨습니다.");
        }
        switch (menuno)
        {
            case 1 -> {
                financeService.getExpenseRecords();
                financeServiceMenu();
            }
            case 2 -> {
                financeService.getYearlyExpenseRecords();
                financeServiceMenu();
            }
            case 3 -> {
                financeService.addExpenseRecord();
                financeServiceMenu();
            }
            case 4 ->{
                financeService.modifyExpenseRecord();
                financeServiceMenu();
            }
            case 5 ->{
                financeService.removeExpenseRecord();
                financeServiceMenu();
            }
            case 6 ->{
                financeService.getRevenueRecords();
                financeServiceMenu();
            }
            case 7 ->{
                financeService.getTotalSettlementRecords();
                financeServiceMenu();
            }
            case 8 ->{
                MemberMenu.memberMainMenu();
            }
            default ->{
                System.out.println("잘못 입력하셨습니다.");
                financeServiceMenu();
            }
        }
    }
}
