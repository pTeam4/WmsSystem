package view;
//입고 메뉴

import service.RetrievalService;
import service.RetrievalServiceImpl;
import config.GetTexts;

public class RetrievalServiceMenu {
    public static void retrievalServiceMenu() {
        RetrievalService retrievalService = new RetrievalServiceImpl();
        int menuno = 0;
        System.out.println("1. 출고요청\t\t2. 출고요청 승인\t\t3. 출고지시서 조회\n4. 출고리스트 조회\t5. 출고 상품검색\t\t6. 운송장 등록\n7. 배차 조회\t\t8. 운송장 수정\t\t9. 운송장 조회\n10. 이전메뉴로 돌아가기");
        try {
            menuno = Integer.parseInt(GetTexts.getInstance().readLine());
        } catch (NumberFormatException e) {
            System.out.println("잘못 입력하셨습니다.");
        }
        switch (menuno) {
            case 1 -> {
                retrievalService.requestRetrieval();
                retrievalServiceMenu();
            }
            case 2 -> {
                retrievalService.approveRetrievalRequest();
                retrievalServiceMenu();
            }
            case 3 ->{
                retrievalService.getRetrievalOrder();
                retrievalServiceMenu();
            }
            case 4 ->{
                retrievalService.getRetrievalList();
                retrievalServiceMenu();
            }
            case 5 ->{
                retrievalService.searchRetrievalGoods();
                retrievalServiceMenu();
            }
            case 6 ->{
                retrievalService.addWaybill();
                retrievalServiceMenu();
            }
            case 7 ->{
                retrievalService.getVehicleDispatching();
                retrievalServiceMenu();
            }
            case 8 ->{
                retrievalService.modifyWaybill();
                retrievalServiceMenu();
            }
            case 9 ->{
                retrievalService.getWaybill();
                retrievalServiceMenu();
            }
            case 10 ->{
                MemberMenu.memberMainMenu();
            }
            default -> {
                System.out.println("잘못입력하셨습니다.");
            }
        }
    }
}
