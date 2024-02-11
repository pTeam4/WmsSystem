package view;

import service.StorageService;
import service.StorageServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//출고 관리 메뉴
public class StorageServiceMenu {
    public static void storageServiceMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StorageService storageService = new StorageServiceImpl();
        int menuno = 0;
        System.out.println("1. 입고요청 2. 입고요청 승인 3. QR바코드 생성 4. 입고요청 취소 5. 입고요청 수정 6. 입고위치 지정 7. 입고날짜 지정 8. 입고고지서 출력 9. 입고 10. 입고현황 11. 기간별 입고 현황 12. 월별입고현황 13. QR바코드 조회 14. 이전 메뉴로 돌아가기");
        try {
            menuno = Integer.parseInt(br.readLine());
        } catch (IOException e) {
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
