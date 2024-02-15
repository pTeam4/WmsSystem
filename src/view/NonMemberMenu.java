package view;

import service.*;
import config.GetTexts;

public class NonMemberMenu {
    private static LoginService loginService = new LoginServiceImpl();
    private static UserService userService = new UserServiceImpl();
    private static VendorService vendorService = new VendorServiceImpl();
    private static RetrievalService retrievalService = new RetrievalServiceImpl();
    private static EstimateService estimateService = new EstimateServiceImpl();
    public static void nonMemberMenu()
    {
        System.out.print(" /\\_/\\    /\\_/\\    /\\_/\\  \n" +
                "( o.o )  ( o.o )  ( o.o ) \n" +
                " > ^ <    > ^ <    > ^ <\n");
        System.out.println("야옹 창고 시스템에 오신 것을 환영합니다. 원하시는 메뉴를 선택해주세요");
        System.out.println("1. 회원가입\t\t2. 로그인\t\t3. 고객센터 \n4. 요금안내조회\t5. 운송장 조회\t6. 견적신청 \n7. 아이디 찾기\t8. 비밀번호 찾기\t9. 시스템 종료");

        int menuno = 0;
        try {
            menuno = Integer.parseInt(GetTexts.getInstance().readLine());
        } catch (NumberFormatException e) {
            System.out.println("잘못 입력하셨습니다.");
        }
        switch (menuno) {
            case 1-> {
                userService.addMember();
                nonMemberMenu();
            }
            case 2-> {
                loginService.login(); // 로그인 검사 로직 필요
                MemberMenu.memberMainMenu();
            }
            case 3-> {
                InquiryServiceMenu.inquiryServiceMenu();
                nonMemberMenu();
            }
            case 4-> {
                vendorService.getFeeInfo();
                nonMemberMenu();
            }
            case 5->{
                retrievalService.getWaybill();
                nonMemberMenu();
            }
            case 6->{
                estimateService.getEstimateRequest();
                nonMemberMenu();
            }
            case 7->{
                userService.findId();
                nonMemberMenu();
            }
            case 8 ->{
                userService.findPassword();
                nonMemberMenu();
            }
            case 9 ->{
                System.out.println("야옹 창고 시스템을 종료합니다.");
            }
            default -> {
                System.out.println("잘못 입력하셨습니다.");
                nonMemberMenu();
            }
        }
    }
}
