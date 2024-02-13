package service;

import config.GetTexts;
import dao.InquiryDao;
import vo.Inquiry;

public class InquiryServiceImpl implements InquiryService {
    InquiryDao inquiryDao = new InquiryDao();
    @Override
    public void getNotice() {

    }

    @Override
    public void addNotice() {

    }

    @Override
    public void modifyNotice() {

    }

    @Override
    public void removeNotice() {

    }

    @Override
    public void getInquiry() {
        inquiryDao.inquirySelect();
    }

    @Override
    public void addInquiry() {
        Inquiry inquiry = new Inquiry();
        System.out.println("[새 게시물 입력]");
        System.out.println("문의 유형 선택 1. 환불문의 2. 창고 사용 문의 3. 기타");
        String postTypeNum = GetTexts.getInstance().readLine();
        String postType = "";
        switch (postTypeNum) {
            case "1" -> {
                postType = "환불 문의";
            }
            case "2" -> {
                postType = "창고 사용 문의";
            }
            case "3" -> {
                postType = "기타";
            }
            default -> {
                System.out.println("잘못 입력하셨습니다.");
            }
        }
        System.out.println("제목: ");
        String postTitle = GetTexts.getInstance().readLine();

        System.out.println("글 내용을 입력하세요. 다 작성하셨으면 exit를 입력해주세요.");
        StringBuilder sb = new StringBuilder();
        String line;

        while (!(line = GetTexts.getInstance().readLine()).equals("exit")) {
            sb.append(line).append("\r\n"); // 각 줄의 끝에 \r\n 추가
        }

        sb.delete(sb.length() - 2, sb.length()); //맨마지막 개행 삭제
        String postContent = sb.toString();

        String userId = "dumplingmani";
        String userName = "구민석";
        inquiry.setPostType(postType);
        inquiry.setPostTitle(postTitle);
        inquiry.setPostContent(postContent);
        inquiry.setUserId(userId);
        inquiry.setUserName(userName);
        inquiryDao.inquiryInsert(inquiry);
    }

    @Override
    public void removeInquiry() {

    }

    @Override
    public void modifyInquiry() {

    }
}
