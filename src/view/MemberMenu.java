package view;

import service.LoginService;
import service.LoginServiceImpl;

import config.GetTexts;

public class MemberMenu {
    public static void memberMainMenu() {
        LoginService loginService = new LoginServiceImpl();
        int menuno = 0;
        System.out.println("1. 회원 관리 2. 로그 아웃 3. 창고 관리 4. 거래처 관리 5. 출고 관리 6. 입고 관리 7. 배차 관리 8. 안전 점검 관리 9. 재무 관리 10. 고객 센터 11. 견적 관리 12. 시스템 종료");
        try {
            menuno = Integer.parseInt(GetTexts.getInstance().readLine());
        } catch (NumberFormatException e) {
            System.out.println("잘못 입력하셨습니다.");
            memberMainMenu();
        }

        switch (menuno) {
            case 1 -> {
                UserServiceMenu.userServiceMenu();
                memberMainMenu();
            }
            case 2 -> {
                loginService.logout();
                NonMemberMenu.nonMemberMenu();
            }
            case 3 -> {
                WarehouseServiceMenu.warehouseServiceMenu();
                memberMainMenu();
            }
            case 4 -> {
                VendorServiceMenu.vendorServiceMenu();
                memberMainMenu();
            }
            case 5 -> {
                RetrievalServiceMenu.retrievalServiceMenu();
                memberMainMenu();
            }
            case 6 -> {
                StorageServiceMenu.storageServiceMenu();
                memberMainMenu();
            }
            case 7 -> {
                VehicleServiceMenu.vehicleServiceMenu();
                memberMainMenu();
            }
            case 8 -> {
                SafetyCheckServiceMenu.safetyCheckServiceMenu();
                memberMainMenu();
            }
            case 9 -> {
                FinanceServiceMenu.financeServiceMenu();
                memberMainMenu();
            }
            case 10 -> {
                InquiryServiceMenu.inquiryServiceMenu();
                memberMainMenu();
            }
            case 11 -> {
                EstimateServiceMenu.estimateServiceMenu();
                memberMainMenu();
            }
            case 12 -> {
                System.out.println("야옹 창고 시스템을 종료합니다.");
                System.exit(0);
            }
            default -> {
                System.out.println("잘못 입력하셨습니다.");
                memberMainMenu();
            }
        }
    }
}