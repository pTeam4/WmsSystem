package view;

import service.FinanceService;
import service.FinanceServiceImpl;
import service.InquiryService;
import service.InquiryServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//고객센터 메뉴
public class InqueryServiceMenu {
    public static void inqueryServiceMenu()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        InquiryService inquiryService = new InquiryServiceImpl();
        int menuno = 0;
        System.out.println("1. 공지사항 조회 2. 공지사항 등록 3. 공지사항 수정 4. 공지사항 삭제 5. 문의게시판 조회 6. 문의게시판 글쓰기 7. 문의게시판 게시물 수정 8. 문의게시판 게시물 삭제 9. 이전메뉴로 돌아가기");
        try {
            menuno = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            System.out.println("잘못 입력하셨습니다.");
        }
        switch (menuno)
        {
            case 1 -> {
                inquiryService.getNotice();
                inqueryServiceMenu();
            }
            case 2 -> {
                inquiryService.addNotice();
                inqueryServiceMenu();
            }
            case 3 -> {
                inquiryService.modifyNotice();
                inqueryServiceMenu();
            }
            case 4 ->{
                inquiryService.removeNotice();
                inqueryServiceMenu();
            }
            case 5 ->{
                inquiryService.getInquiry();
                inqueryServiceMenu();
            }
            case 6 ->{
                inquiryService.addInquiry();
                inqueryServiceMenu();
            }
            case 7 ->{
                inquiryService.modifyInquiry();
                inqueryServiceMenu();
            }
            case 8 ->{
                inquiryService.removeInquiry();
                inqueryServiceMenu();
            }
            case 9 ->
            {
                MemberMenu.memberMainMenu();
            }
            default ->{
                System.out.println("잘못 입력하셨습니다.");
                inqueryServiceMenu();
            }
        }
    }
}
