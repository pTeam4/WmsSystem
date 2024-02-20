package service;

import util.GetTexts;
import dao.InquiryDao;
import dao.NoticeDao;
import util.UserManager;
import vo.Inquiry;
import vo.Notice;

import java.util.ArrayList;

public class InquiryServiceImpl implements InquiryService {
    InquiryDao inquiryDao = new InquiryDao();
    NoticeDao noticeDao = new NoticeDao();

    /**
     * 공지사항 게시물 조회
     */
    @Override
    public void getNotice() {
        System.out.println("[공지 사항]");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.printf("%-6s%-10s%-12s%-15s%-25s%-16s\n", "게시물번호", "공지종류", "제목", "ID", "내용", "작성일");
        System.out.println("----------------------------------------------------------------------------------------");
        ArrayList<Notice> noticeList = noticeDao.noticeSelect();
        for (Notice notice : noticeList)
        {
            String content = notice.getContent().replace("\r\n", " ");
            if(content.length() > 10)
            {
                content = content.substring(0, 10) + "...";
            }
            System.out.printf("%-6s%-10s%-12s%-15s%-25s%-16s\n", notice.getNo(), notice.getType(), notice.getTitle(), notice.getUserId(), content, notice.getNoticeDate());
        }
        System.out.println("----------------------------------------------------------------------------------------\n");
    }

    /**
     * 공지사항 게시물 등록
     */
    @Override
    public void addNotice() {
        if(UserManager.getInstance().getCurrentUser().getPermission() != 1)
        {
            System.out.println("권한이 없습니다.");
            return;
        }
        Notice notice = new Notice();
        System.out.println("[새 공지사항 입력]");
        System.out.println("공지 유형 선택 1. 긴급 2. 정기 공지 3. 이용 안내");
        String postTypeNum = GetTexts.getInstance().readLine();
        String postType = "";
        switch (postTypeNum) {
            case "1" -> {
                postType = "긴급";
            }
            case "2" -> {
                postType = "정기 공지";
            }
            case "3" -> {
                postType = "이용 안내";
            }
            default -> {
                System.out.println("잘못 입력하셨습니다.");
                return;
            }
        }
        System.out.println("제목: ");
        String title = GetTexts.getInstance().readLine();

        System.out.println("글 내용을 입력하세요. 다 작성하셨으면 exit를 입력해주세요.");
        StringBuilder sb = new StringBuilder();
        String line;

        while (!(line = GetTexts.getInstance().readLine()).equals("exit")) {
            sb.append(line).append("\r\n"); // 각 줄의 끝에 \r\n 추가
        }

        sb.delete(sb.length() - 2, sb.length()); //맨마지막 개행 삭제
        String content = sb.toString();

        String userId = UserManager.getInstance().getCurrentUser().getId();
        notice.setType(postType);
        notice.setUserId(userId);
        notice.setContent(content);
        notice.setTitle(title);
        int row = noticeDao.noticeInsert(notice);
        if (row != 0) System.out.println("공지가 등록되었습니다.");
    }

    /**
     * 공지사항 게시물 수정
     */
    @Override
    public void modifyNotice() {
        if(UserManager.getInstance().getCurrentUser().getPermission() != 1)
        {
            System.out.println("권한이 없습니다.");
            return;
        }
        System.out.println("수정할 공지사항 번호를 입력하세요.");
        int no = Integer.parseInt(GetTexts.getInstance().readLine());
        Notice notice = noticeDao.noticeSelectOne(no);
        System.out.println("[수정할 공지사항 입력]");
        System.out.println("공지 유형 선택 1. 긴급 2. 정기 공지 3. 이용 안내");
        String postTypeNum = GetTexts.getInstance().readLine();
        String postType = "";
        switch (postTypeNum) {
            case "1" -> {
                postType = "긴급";
            }
            case "2" -> {
                postType = "정기 공지";
            }
            case "3" -> {
                postType = "이용 안내";
            }
            default -> {
                System.out.println("잘못 입력하셨습니다.");
                return;
            }
        }
        System.out.println("제목: ");
        String title = GetTexts.getInstance().readLine();

        System.out.println("글 내용을 입력하세요. 다 작성하셨으면 exit를 입력해주세요.");
        StringBuilder sb = new StringBuilder();
        String line;

        while (!(line = GetTexts.getInstance().readLine()).equals("exit")) {
            sb.append(line).append("\r\n"); // 각 줄의 끝에 \r\n 추가
        }

        sb.delete(sb.length() - 2, sb.length()); //맨마지막 개행 삭제
        String content = sb.toString();

        String userId = UserManager.getInstance().getCurrentUser().getId();
        notice.setType(postType);
        notice.setUserId(userId);
        notice.setContent(content);
        notice.setTitle(title);
        int row = noticeDao.noticeUpdate(notice);
        if (row != 0) System.out.println("공지가 수정되었습니다.");
    }

    /**
     * 공지사항 게시물 삭제
     */
    @Override
    public void removeNotice() {
        if(UserManager.getInstance().getCurrentUser().getPermission() != 1)
        {
            System.out.println("권한이 없습니다.");
            return;
        }
        System.out.print("삭제할 공지사항 번호를 입력하세요. ");
        int no = Integer.parseInt(GetTexts.getInstance().readLine());
        int row = noticeDao.noticeDelete(no);
        if(row != 0){
            System.out.println("해당 게시물이 삭제되었습니다.");
        }
        else{
            System.out.println("해당 게시물이 없습니다.");
        }
    }

    /**
     * 문의게시판 게시물 조회
     */
    @Override
    public void getInquiry() {
        System.out.println("[문의 게시판 게시물 목록]");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.printf("%-6s%-10s%-12s%-15s%-25s%-16s%-16s%-16s\n", "게시물번호", "문의유형", "제목", "ID", "작성자이름", "내용", "작성일", "답변");
        System.out.println("----------------------------------------------------------------------------------------");
        ArrayList<Inquiry> inquiries = inquiryDao.inquirySelect();
        for (Inquiry inquiry : inquiries)
        {
            String content = inquiry.getPostContent().replace("\r\n", " ");
            if(content.length() > 10)
            {
                content = content.substring(0, 10) + "...";
            }
            String response = inquiry.getResponse();
            if(response == null)
            {
                response = "답변 대기중";
            }
            else {
                if(response.length() > 10)
                {
                    response = response.substring(0, 10) + "...";
                }
            }
            System.out.printf("%-6s%-10s%-12s%-15s%-25s%-16s%-16s%-16s\n",
                    inquiry.getPostNum(),
                    inquiry.getPostType(),
                    inquiry.getPostTitle(),
                    inquiry.getUserId(),
                    inquiry.getUserName(),
                    content,
                    inquiry.getPostDate(),
                    response);
        }
        System.out.println(
                "----------------------------------------------------------------------------------------\n");
    }

    /**
     * 문의게시판 게시물 작성
     */
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

        String userId = UserManager.getInstance().getCurrentUser().getId();
        String userName = UserManager.getInstance().getCurrentUser().getName();
        inquiry.setPostType(postType);
        inquiry.setPostTitle(postTitle);
        inquiry.setPostContent(postContent);
        inquiry.setUserId(userId);
        inquiry.setUserName(userName);
        int row = inquiryDao.inquiryInsert(inquiry);
        if (row != 0)System.out.println("문의글이 등록되었습니다.");
    }

    /**
     * 문의게시판 게시물 수정
     */
    @Override
    public void removeInquiry() {
        System.out.print("삭제할 게시물 번호를 입력하세요. ");
        int postNum = Integer.parseInt(GetTexts.getInstance().readLine());
        int row = inquiryDao.inquiryDelete(postNum);
        if(row != 0){
            System.out.println("해당 게시물이 삭제되었습니다.");
        }
        else{
            System.out.println("해당 게시물이 없습니다.");
        }
    }

    /**
     * 문의게시판 게시물 삭제
     */
    @Override
    public void modifyInquiry() {
        System.out.println("수정할 게시물 번호를 입력하세요.");
        int postNum = Integer.parseInt(GetTexts.getInstance().readLine());
        Inquiry inquiry = new Inquiry();
        System.out.println("[수정할 게시물 입력]");
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

        inquiry.setPostNum(postNum);
        inquiry.setPostType(postType);
        inquiry.setPostTitle(postTitle);
        inquiry.setPostContent(postContent);
        inquiry.setUserId(UserManager.getInstance().getCurrentUser().getId());
        inquiry.setUserName(UserManager.getInstance().getCurrentUser().getName());
        int row = inquiryDao.inquiryUpdate(inquiry);
        if(row != 0)
        {
            System.out.println("게시물 수정 성공");
        }
        else{
            System.out.println("게시물 수정 실패");
        }
    }
}
