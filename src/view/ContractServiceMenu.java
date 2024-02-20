package view;

import service.VendorService;
import service.VendorServiceImpl;
import util.GetTexts;

public class ContractServiceMenu {
    public static void contractServiceMenu() {
        VendorService vendorService = new VendorServiceImpl();
        int menuno = 0;
        System.out.println("1. 계약 조회 2. 계약 승인 3. 계약 취소 4. 신청 계약 정보 조회 5. 계약 정보 캘린더 관리 6. 이전 메뉴로 돌아가기");
        try {
            menuno = Integer.parseInt(GetTexts.getInstance().readLine());
        } catch (NumberFormatException e) {
            System.out.println("잘못 입력하셨습니다.");
        }
        switch (menuno) {
            case 1 -> {
                vendorService.getAllContracts();
                contractServiceMenu();
            }
            case 2 -> {
                vendorService.approveContract();
                contractServiceMenu();
            }
            case 3 -> {
                vendorService.cancelContract();
                contractServiceMenu();
            }
            case 4 -> {
                vendorService.getContractsByIndividual();
                contractServiceMenu();
            }
            case 5 -> {
                vendorService.manageContractCalendar();
                contractServiceMenu();
            }
            case 6 -> {
                VendorServiceMenu.vendorServiceMenu();
            }
            default -> {
                System.out.println("잘못 입력하셨습니다.");
            }
        }
    }
}
