package service;

import config.GetTexts;
import config.UserManager;
import dao.UserDao;
import vo.User;

public class LoginServiceImpl implements LoginService {
    UserDao userDao = new UserDao();
    GetTexts getTexts = GetTexts.getInstance();

    @Override
    public void login() {
        System.out.print("id: ");
        String id = getTexts.readLine();
        System.out.print("pw: ");
        String pw = getTexts.readLine();
        userDao.userSelectOne(id, pw);
        UserManager userManager = UserManager.getInstance();
        User currentUser = userManager.getCurrentUser();
        System.out.println(currentUser.getName() + " 로그인 성공");
    }

    @Override
    public void logout() {
        UserManager userManager = UserManager.getInstance();
        User currentUser = userManager.getCurrentUser();
        currentUser = null;
        System.out.println("로그아웃 되었습니다.");
    }
}
