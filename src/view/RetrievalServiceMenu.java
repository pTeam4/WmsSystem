package view;
//입고 메뉴

import service.RetrievalService;
import service.RetrievalServiceImpl;
import config.GetTexts;

//import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;

public class RetrievalServiceMenu {
    public static void retrievalServiceMenu() {
        RetrievalService retrievalService = new RetrievalServiceImpl();
        int menuno = 0;
        System.out.println("1. 출고요청 2. 출고요청 승인 3. 출고지시서 조회 4. 출고리스트 조회 5. 출고 상품검색 6. 운송장 등록 7. 배차 조회 8. 배차 취소 9. 배차 수정 10. 운송장 수정 11. 운송장 조회 12. 이전메뉴로 돌아가기");
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
                retrievalService.cancelVehicleDispatching();
                retrievalServiceMenu();
            }
            case 9 ->{
                retrievalService.modifyVehicleDispatching();
                retrievalServiceMenu();
            }
            case 10 ->{
                retrievalService.modifyWaybill();
                retrievalServiceMenu();
            }
            case 11 ->{
                retrievalService.getWaybill();
                retrievalServiceMenu();
            }
            case 12 ->{
                MemberMenu.memberMainMenu();
            }
            default -> {
                System.out.println("잘못입력하셨습니다.");
            }
        }
    }
}
