package view;

import service.VendorService;
import service.VendorServiceImpl;
import config.GetTexts;

public class VendorServiceDetailMenu {
    public static void vendorServiceDetailMenu() {
        VendorService vendorService = new VendorServiceImpl();
        int menuno = 0;
        System.out.println("1. 거래처 권한 승인 신청 2. 거래처 정보 조회 3. 거래처 권한 승인 4. 이전 메뉴로 돌아가기");
        try {
            menuno = Integer.parseInt(GetTexts.getInstance().readLine());
        } catch (NumberFormatException e) {
            System.out.println("잘못 입력하셨습니다.");
        }
        switch (menuno) {
            case 1 -> {
                vendorService.addVendor();
                vendorServiceDetailMenu();
            }
            case 2 -> {
                vendorService.getVendorAuthority();
                vendorServiceDetailMenu();
            }
            case 3 -> {
                vendorService.approveVendorAuthority();
                vendorServiceDetailMenu();
            }
            case 4 -> {
                VendorServiceMenu.vendorServiceMenu();
            }
            default -> {
                System.out.println("잘못 입력하셨습니다.");
                vendorServiceDetailMenu();
            }
        }
    }
}
