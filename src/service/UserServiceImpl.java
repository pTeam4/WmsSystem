package service;

import util.GetTexts;
import dao.UserDao;
import dto.UserPermission;
import util.UserManager;
import vo.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDao();
    GetTexts getTexts = GetTexts.getInstance();

    @Override
    public void addMember() {
        User user = new User();
        System.out.print("이름: ");
        user.setName(getTexts.readLine());
        System.out.print("생년월일(ex: 20240101): ");
        user.setBirth(stringToDate(getTexts.readLine()));
        user.setId(checkId());
        user.setPw(checkPw());
        user.setEmail(checkEmail());
        user.setTel(checkTel());
        userDao.userInsert(user);
        System.out.println(user.getName() + "님 회원가입이 완료되었습니다.");
    }

//    public String checkBirth() {
//        System.out.print("생년월일(ex: 20240101): ");
//        String input = getTexts.readLine();
//        if (!Pattern.matches("\\d{8}", input)) {
//            System.out.println("입력한 생년월일은 유효하지 않습니다. 다시 입력해주세요.");
//            input = null;
//            checkBirth();
//        }
//        return input;
//    }

    public String checkId() {
        System.out.print("id(영어+숫자, 4~12자): ");
        String input = getTexts.readLine();
        if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,12}$", input)) {
            System.out.println("입력한 ID는 유효하지 않습니다. 다시 입력해주세요.");
            checkId();
        }
        return input;
    }

    public String checkPw() {
        System.out.print("pw(영어+숫자, 4~12자): ");
        String input = getTexts.readLine();
        if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,12}$", input)) {
            System.out.println("입력한 pw는 유효하지 않습니다. 다시 입력해주세요.");
            checkPw();
        }
        return input;
    }

    public String checkEmail() {
        System.out.print("email(ex: ssg@gmail.com): ");
        String input = getTexts.readLine();
        if (!Pattern.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", input)) {
            System.out.println("입력한 Email은 유효하지 않습니다. 다시 입력해주세요.");
            checkEmail();
        }
        return input;
    }

    public String checkTel() {
        System.out.print("tel(ex: 01012345678): ");
        String input = getTexts.readLine();
        if (!Pattern.matches("^01[0-9]{9}$", input)) {
            System.out.println("입력한 전화번호는 유효하지 않습니다. 다시 입력해주세요.");
            checkTel();
        }
        return input;
    }

    @Override
    public void findId() {
        System.out.println("아이디 찾기");
        System.out.print("회원 이름: ");
        String name = getTexts.readLine();
        System.out.print("회원 이메일(ex: ssg@gmail.com): ");
        String email = getTexts.readLine();
        String id = userDao.userSelectByNameAndEmail(name, email);
        System.out.println("찾으시는 id: " + id);

    }

    @Override
    public void findPassword() {
        System.out.println("비밀번호 찾기");
        System.out.print("회원 아이디: ");
        String id = getTexts.readLine();
        System.out.print("회원 이름: ");
        String name = getTexts.readLine();
        String pw = userDao.userSelectByIdAndName(id, name);
        System.out.println("찾으시는 pw: " + pw);

    }

    public Date stringToDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date utilDate = null;
        try {
            utilDate = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return utilDate;
    }

    @Override
    public void modifyMember() {
        User user = new User();
        System.out.println("회원정보 수정");
        System.out.print("name: ");
        user.setName(getTexts.readLine());
        System.out.print("birth(ex: 20240101): ");
        user.setBirth(stringToDate(getTexts.readLine()));
        System.out.print("pw: ");
        user.setPw(getTexts.readLine());
        System.out.print("email(ex: ssg@gmail.com): ");
        user.setEmail(getTexts.readLine());
        System.out.print("tel(ex: 01012345678): ");
        user.setTel(getTexts.readLine());
//        user.setPw(checkPw());
//        user.setEmail(checkEmail());
//        user.setTel(checkTel());
        userDao.userUpdate(user, UserManager.getInstance().getCurrentUser().getId());
        System.out.println("회원정보 수정 완료");
    }

    @Override
    public void removeMember() {

    }

    @Override
    public void approveAdministrator() {
        System.out.println("관리자 승인");
        getMembers();
        System.out.print("가입을 승인할 회원 id: ");
        String id = getTexts.readLine();
        userDao.userConfirm(id);
        System.out.println("변경 완료 되었습니다.");
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
        String format = "%-20s | %-20s | %-20s | %-20s | %-20s | %-20s\n";
        System.out.printf(format, "id", "name", "birth", "pw", "email", "tel");
        System.out.println("-".repeat(150));
        System.out.printf(format, user.getId(), user.getName(), user.getBirth(), user.getPw(), user.getEmail(), user.getTel());
        System.out.println("-".repeat(150));

    }

    @Override
    public void getMembersByAuthority() {

    }

    @Override
    public void getMembers() {
        List<UserPermission> users = userDao.userSelect();
        String format = "%-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-5s\n";
        System.out.println("-".repeat(180));
        System.out.printf(format, "id", "name", "birth", "pw", "email", "tel", "permission", "status");
        System.out.println("-".repeat(180));
        users.forEach(user -> {
            System.out.printf(format, user.getId(), user.getName(), user.getBirth(), user.getPw(), user.getEmail(), user.getTel(), user.getLevel(), user.getState());
        });
        System.out.println("-".repeat(180));
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
