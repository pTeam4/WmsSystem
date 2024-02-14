package view;

import service.InquiryService;
import service.InquiryServiceImpl;
import config.GetTexts;

//고객센터 메뉴
public class InquiryServiceMenu {
    public static void inquiryServiceMenu() {
        InquiryService inquiryService = new InquiryServiceImpl();
        int menuno = 0;
        System.out.println("1. 공지사항 조회 2. 공지사항 등록 3. 공지사항 수정 4. 공지사항 삭제 5. 문의게시판 조회 6. 문의게시판 글쓰기 7. 문의게시판 게시물 수정 8. 문의게시판 게시물 삭제 9. 이전메뉴로 돌아가기");
        try {
            menuno = Integer.parseInt(GetTexts.getInstance().readLine());
        } catch (NumberFormatException e) {
            System.out.println("잘못 입력하셨습니다.");
        }
        switch (menuno) {
            case 1 -> {
                inquiryService.getNotice();
                inquiryServiceMenu();
            }
            case 2 -> {
                inquiryService.addNotice();
                inquiryServiceMenu();
            }
            case 3 -> {
                inquiryService.modifyNotice();
                inquiryServiceMenu();
            }
            case 4 -> {
                inquiryService.removeNotice();
                inquiryServiceMenu();
            }
            case 5 -> {
                inquiryService.getInquiry();
                inquiryServiceMenu();
            }
            case 6 -> {
                inquiryService.addInquiry();
                inquiryServiceMenu();
            }
            case 7 -> {
                inquiryService.modifyInquiry();
                inquiryServiceMenu();
            }
            case 8 -> {
                inquiryService.removeInquiry();
                inquiryServiceMenu();
            }
            case 9 -> {
                MemberMenu.memberMainMenu();
            }
            default -> {
                System.out.println("잘못 입력하셨습니다.");
                inquiryServiceMenu();
            }
        }
    }
}