package service;

import config.GetTexts;
import util.UserManager;
import dao.UserDao;
import vo.User;

public class LoginServiceImpl implements LoginService {
    UserDao userDao = new UserDao();
    GetTexts getTexts = GetTexts.getInstance();

    @Override
    public void login() {
        try {
            System.out.print("id: ");
            String id = getTexts.readLine();

            System.out.print("pw: ");
            String pw = getTexts.readLine();

            User user = userDao.userSelectOne(id, pw);

            UserManager.getInstance().setCurrentUser(user);
            User currentUser = UserManager.getInstance().getCurrentUser();

            System.out.println(currentUser.getName() + " 님 환영합니다.");
        } catch (NullPointerException e) {
            System.out.println("id 혹은 pw가 잘못 입력되었습니다.");
            login();
        }
    }

    @Override
    public void logout() {
        UserManager userManager = UserManager.getInstance();
        User currentUser = userManager.getCurrentUser();
        currentUser = null;
        System.out.println("로그아웃 되었습니다.");
    }
}
