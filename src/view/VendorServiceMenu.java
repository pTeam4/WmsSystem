package view;

import service.VendorService;
import service.VendorServiceImpl;
import config.GetTexts;

public class VendorServiceMenu {
    public static void vendorServiceMenu() {
        VendorService vendorService = new VendorServiceImpl();
        int menuno = 0;
        System.out.println("1. 요금 안내 2. 거래처 업무 3. 계약 업무 4. 이전메뉴로 돌아가기");
        try {
            menuno = Integer.parseInt(GetTexts.getInstance().readLine());
        } catch (NumberFormatException e) {
            System.out.println("잘못 입력하셨습니다.");
        }
        switch (menuno) {
            case 1 -> {
                FeeInformationServiceMenu.feeInformationServiceMenu();
            }
            case 2 -> {
                VendorServiceDetailMenu.vendorServiceDetailMenu();
            }
            case 3 -> {
                ContractServiceMenu.contractServiceMenu();
            }
            case 4 -> {
                MemberMenu.memberMainMenu();
            }
            default -> {
                System.out.println("잘못 입력하셨습니다.");
                vendorServiceMenu();
            }
        }
    }
}
