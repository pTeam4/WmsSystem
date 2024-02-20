package view;

import service.StorageService;
import service.StorageServiceImpl;
import util.GetTexts;

//출고 관리 메뉴
public class StorageServiceMenu {
    public static void storageServiceMenu() {
        StorageService storageService = new StorageServiceImpl();
        int menuno = 0;
        System.out.println("1. 입고요청\t\t2. 입고요청 승인\t\t3. QR바코드 생성\n4. 입고요청 취소\t5. 입고요청 수정\t\t6. 입고위치 지정\n7. 입고날짜 지정\t8. 입고고지서 출력\t\t9. 입고\n10. 입고현황\t\t11. 기간별 입고 현황\t12. 월별입고현황\n13. QR바코드 조회\t14. 이전 메뉴로 돌아가기");
        try {
            menuno = Integer.parseInt(GetTexts.getInstance().readLine());
        } catch (NumberFormatException e) {
            System.out.println("잘못 입력하셨습니다.");
        }
        switch (menuno) {
            case 1 ->{
                storageService.requestStorage();
                storageServiceMenu();
            }
            case 2 ->{
                storageService.approveStorageRequest();
                storageServiceMenu();
            }
            case 3 ->{
                storageService.createQrBarcode();
                storageServiceMenu();
            }
            case 4 ->{
                storageService.cancelStorageRequest();
                storageServiceMenu();
            }
            case 5 ->{
                storageService.modifyStorageRequest();
                storageServiceMenu();
            }
            case 6 ->{
                storageService.designateStorageLocation();
                storageServiceMenu();
            }
            case 7 ->{
                storageService.designateStorageDate();
                storageServiceMenu();
            }
            case 8 ->{
                storageService.printStorageNotice();
                storageServiceMenu();
            }
            case 9 ->{
                storageService.storage();
                storageServiceMenu();
            }
            case 10 ->{
                storageService.storageStatus();
                storageServiceMenu();
            }
            case 11 ->{
                storageService.storageStatusByPeriod();
                storageServiceMenu();
            }
            case 12 ->{
                storageService.storageStatusMonthly();
                storageServiceMenu();
            }
            case 13 ->{
                storageService.getQrBarcode();
                storageServiceMenu();
            }
            case 14 ->{
                MemberMenu.memberMainMenu();
            }
            default -> {
                System.out.println("잘못 입력하셨습니다.");
                storageServiceMenu();
            }
        }

    }
}
