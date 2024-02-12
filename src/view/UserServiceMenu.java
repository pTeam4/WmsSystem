package view;

import service.UserService;
import service.UserServiceImpl;
import config.GetTexts;

public class UserServiceMenu {
    public static void userServiceMenu() {
        UserService userService = new UserServiceImpl();
        int menuno = 0;
        System.out.println("1. 회원정보 수정 2. 회원 탈퇴 3. 관리자 승인 4. 회원 승인 5. 거래처 미등록 회원 조회 6. 관리자 조회 7. 회원 상세보기 8. 회원 권한별 조회 9. 회원 조회 10. 회원 권한 등록 11. 회원 권한 수정 12. 회원 권한 삭제 13. 회원 권한 조회 14. 이전 메뉴로 돌아가기");
        try {
            menuno = Integer.parseInt(GetTexts.getInstance().readLine());
        } catch (NumberFormatException e) {
            System.out.println("잘못 입력하셨습니다.");
        }
        switch (menuno) {
            case 1 -> {
                userService.modifyMember();
                userServiceMenu();
            }
            case 2 -> {
                userService.removeMember();
                userServiceMenu();
            }
            case 3 -> {
                userService.approveAdministrator();
                userServiceMenu();
            }
            case 4 -> {
                userService.approveMember();
                userServiceMenu();
            }
            case 5 -> {
                userService.getUnregisteredMembersByVendor();
                userServiceMenu();
            }
            case 7 -> {
                userService.getMemberDetails();
                userServiceMenu();
            }
            case 8 -> {
                userService.getMembersByAuthority();
                userServiceMenu();
            }
            case 9 -> {
                userService.getMembers();
                userServiceMenu();
            }
            case 10 -> {
                userService.addMemberAuthority();
                userServiceMenu();
            }
            case 11 -> {
                userService.modifyMemberAuthority();
                userServiceMenu();
            }
            case 12 -> {
                userService.removeMemberAuthority();
                userServiceMenu();
            }
            case 13 -> {
                userService.getMemberAuthority();
                userServiceMenu();
            }
            case 14 -> {
                MemberMenu.memberMainMenu();
            }
            default -> {
                System.out.println("잘못 입력하셨습니다.");
                userServiceMenu();
            }
        }
    }
}
