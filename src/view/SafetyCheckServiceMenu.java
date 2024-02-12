package view;

import service.SafetyCheckService;
import service.SafetyCheckServiceImpl;
import config.GetTexts;

//안전점검메뉴
public class SafetyCheckServiceMenu {
    public static void safetyCheckServiceMenu() {
        SafetyCheckService safetyCheckService = new SafetyCheckServiceImpl();
        int menuno = 0;
        System.out.println("1. 안전점검 실시내역 등록 2. 안전점검 내역 조회 3. 안전점검 내역 수정 4. 안전점검 내역 삭제 5. 후속조치 기록 6. 이전메뉴로 돌아가기");
        try {
            menuno = Integer.parseInt(GetTexts.getInstance().readLine());
        } catch (NumberFormatException e) {
            System.out.println("잘못 입력하셨습니다.");
        }
        switch (menuno) {
            case 1 -> {
                safetyCheckService.addSafetyCheck();
                safetyCheckServiceMenu();
            }
            case 2 -> {
                safetyCheckService.getSafetyCheckRecords();
                safetyCheckServiceMenu();
            }
            case 3 -> {
                safetyCheckService.modifySafetyCheckRecord();
                safetyCheckServiceMenu();
            }
            case 4 -> {
                safetyCheckService.removeSafetyCheckRecord();
                safetyCheckServiceMenu();
            }
            case 5 -> {
                safetyCheckService.recordTakeFollowUpAction();
                safetyCheckServiceMenu();
            }
            case 6 -> {
                MemberMenu.memberMainMenu();
            }
            default -> {
                System.out.println("잘못 입력하셨습니다.");
            }
        }
    }
}
