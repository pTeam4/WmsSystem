package view;

import service.EstimateService;
import service.EstimateServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//견적 메뉴
public class EstimateServiceMenu {
    public static void estimateServiceMenu()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        EstimateService estimateService = new EstimateServiceImpl();
        int menuno = 0;
        System.out.println("1. 견적신청 2. 견적신청내역조회 3. 견적신청 내역 검토 4. 이전메뉴로 돌아가기");
        try {
            menuno = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            System.out.println("잘못 입력하셨습니다.");
        }
        switch (menuno)
        {
            case 1 -> {
                estimateService.requestEstimate();
                estimateServiceMenu();
            }
            case 2 -> {
                estimateService.getEstimateRequest();
                estimateServiceMenu();
            }
            case 3 -> {
                estimateService.reviewEstimateRequestHistory();
                estimateServiceMenu();
            }
            case 4 ->{
                MemberMenu.memberMainMenu();
            }
            default ->{
                System.out.println("잘못 입력하셨습니다.");
                estimateServiceMenu();
            }
        }
    }
}
