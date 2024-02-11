package view;
//입고 메뉴

import service.RetrievalService;
import service.RetrievalServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RetrievalServiceMenu {
    public static void retrievalServiceMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        RetrievalService retrievalService = new RetrievalServiceImpl();
        int menuno = 0;
        System.out.println("1. 출고요청 2. 출고요청 승인 3. 출고지시서 조회 4. 출고리스트 조회 5. 출고 상품검색 6. 배차등록 7. 운송장 등록 8. 배차 조회 9. 배차 취소 10. 배차 수정 11. 운송장 수정 12. 운송장 조회 13. 이전메뉴로 돌아가기");
        try {
            menuno = Integer.parseInt(br.readLine());
        } catch (IOException e) {
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
                retrievalService.addVehicleDispatching();
                retrievalServiceMenu();
            }
            case 7 ->{
                retrievalService.addWaybill();
                retrievalServiceMenu();
            }
            case 8 ->{
                retrievalService.getVehicleDispatching();
                retrievalServiceMenu();
            }
            case 9 ->{
                retrievalService.cancelVehicleDispatching();
                retrievalServiceMenu();
            }
            case 10 ->{
                retrievalService.modifyVehicleDispatching();
                retrievalServiceMenu();
            }
            case 11 ->{
                retrievalService.modifyWaybill();
                retrievalServiceMenu();
            }
            case 12 ->{
                retrievalService.getWaybill();
                retrievalServiceMenu();
            }
            case 13 ->{
                MemberMenu.memberMainMenu();
            }
            default -> {
                System.out.println("잘못입력하셨습니다.");
            }
        }
    }
}
