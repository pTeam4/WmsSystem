import dao.UserDao;
import service.LoginServiceImpl;
import service.UserService;
import service.UserServiceImpl;
import view.MemberMenu;
import view.NonMemberMenu;

// 메인 클래스는 프로그램의 진입점이며, WMS 시스템의 메인 메뉴를 표시하는 역할을 합니다.
public class Main {
    public static void main(String[] args) {

        NonMemberMenu wmsMenu = new NonMemberMenu();
//        wmsMenu.nonMemberMenu();

//        UserServiceImpl userService = new UserServiceImpl();
//        userService.addMember();

        MemberMenu memberMenu = new MemberMenu();
        memberMenu.memberMainMenu();


    }
}
