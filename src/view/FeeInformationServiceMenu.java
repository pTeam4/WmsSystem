package view;

import service.VendorService;
import service.VendorServiceImpl;
import config.GetTexts;

public class FeeInformationServiceMenu {

    public static void feeInformationServiceMenu() {
        VendorService vendorService = new VendorServiceImpl();
        System.out.println("1. 사용 요금 정보 조회 2. 요금안내 정보 등록 3. 요금안내 정보 수정 4. 요금안내 정보 삭제 5. 이전 메뉴로 돌아가기");
        int menuno = 0;
        try {
            menuno = Integer.parseInt(GetTexts.getInstance().readLine());
        } catch (NumberFormatException e) {
            System.out.println("잘못입력하셨습니다.");
        }
        switch (menuno) {
            case 1 -> {
                vendorService.getFeeInfo();
                feeInformationServiceMenu();
            }
            case 2 -> {
                vendorService.addFeeInfo();
                feeInformationServiceMenu();
            }
            case 3 -> {
                vendorService.modifyFeeInfo();
                feeInformationServiceMenu();
            }
            case 4 -> {
                vendorService.removeFeeInfo();
                feeInformationServiceMenu();
            }
            case 5 -> {
                VendorServiceMenu.vendorServiceMenu();
            }
            default -> {
                feeInformationServiceMenu();
            }
        }
    }
}
