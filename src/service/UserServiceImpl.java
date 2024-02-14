package service;

import config.GetTexts;
import dao.UserDao;
import util.UserManager;
import vo.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDao();
    GetTexts getTexts = GetTexts.getInstance();

    @Override
    public void addMember() {
        User user = new User();

        System.out.print("이름: ");
        user.setName(getTexts.readLine());

        System.out.print("생년월일(ex: 19981225): ");
        String dateString = getTexts.readLine();

        // SimpleDateFormat을 사용하여 문자열을 java.util.Date로 파싱
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date utilDate;

        try {
            utilDate = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        user.setBirth(utilDate);

        System.out.print("id: ");
        user.setId(getTexts.readLine());

        System.out.print("pw: ");
        user.setPw(getTexts.readLine());

        System.out.print("email: ");
        user.setEmail(getTexts.readLine());

        System.out.print("tel: ");
        user.setTel(getTexts.readLine());

        userDao.userInsert(user);
        System.out.println(user.getName() + "님 회원가입이 완료되었습니다.");
    }

    public String checkId() {
        //사용자 ID에 대한 정규식
        String regex = "^[a-zA-Z0-9]{4,12}$";
        //정규식 패턴을 컴파일
        Pattern pattern = Pattern.compile(regex);
        System.out.print("id(영어+숫자, 4~12자): ");
        String input = getTexts.readLine();
        // 패턴과 입력 문자열을 비교하여 일치 여부 확인
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            System.out.println("입력한 ID는 유효하지 않습니다. 다시 입력해주세요.");
            checkId();
            return null;
        }
        return input;
    }

    public void checkPw() {
    }

    public void checkEmail() {
    }

    public void checkTel() {
    }

    @Override
    public void findId() {

    }

    @Override
    public void findPassword() {

    }

    @Override
    public void modifyMember() {
        User user = new User();
        System.out.println("회원정보 수정");
        System.out.print("name : ");
        user.setName(getTexts.readLine());
        System.out.print("birth : ");
        String dateString = getTexts.readLine();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date utilDate;
        try {
            utilDate = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        user.setBirth(utilDate);
        System.out.print("pw : ");
        user.setPw(getTexts.readLine());
        System.out.print("email : ");
        user.setEmail(getTexts.readLine());
        System.out.print("tel : ");
        user.setTel(getTexts.readLine());
        userDao.userUpdate(user, UserManager.getInstance().getCurrentUser().getId());
        System.out.println("회원정보 수정 완료");
    }

    @Override
    public void removeMember() {

    }

    @Override
    public void approveAdministrator() {

    }

    @Override
    public void approveMember() {

    }

    @Override
    public void getUnregisteredMembersByVendor() {

    }

    @Override
    public void getMemberDetails() {
        User user = UserManager.getInstance().getCurrentUser();
        System.out.printf("%-20s | %-20s | %-20s | %-20s | %-20s | %-20s\n", "id", "name", "birth", "pw", "email", "tel");
        System.out.println("-".repeat(150));
        System.out.printf("%-20s | %-20s | %-20s | %-20s | %-20s | %-20s\n"
                , user.getId(), user.getName(), user.getBirth(), user.getPw(), user.getEmail(), user.getTel());

    }

    @Override
    public void getMembersByAuthority() {

    }

    @Override
    public void getMembers() {
        List<User> users = userDao.userSelect();
        System.out.printf("%-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-10s | %-5s\n", "id", "name", "birth", "pw", "email", "tel", "permission", "status");
        System.out.println("-".repeat(180));
        users.forEach(user -> {
            System.out.printf("%-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-5s\n"
                    , user.getId(), user.getName(), user.getBirth(), user.getPw(), user.getEmail(), user.getTel(), user.getPermission(), user.getStatus());
        });
    }

    @Override
    public void addMemberAuthority() {

    }

    @Override
    public void removeMemberAuthority() {

    }

    @Override
    public void modifyMemberAuthority() {

    }

    @Override
    public void getMemberAuthority() {

    }
}
