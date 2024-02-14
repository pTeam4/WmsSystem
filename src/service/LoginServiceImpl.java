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
        System.out.print("id: ");
        String id = getTexts.readLine();

        System.out.print("pw: ");
        String pw = getTexts.readLine();

        User user = userDao.userSelectOne(id, pw);

        UserManager.getInstance().setCurrentUser(user);
        User currentUser = UserManager.getInstance().getCurrentUser();

        System.out.println(currentUser.getName() + " 로그인 성공");
    }

    @Override
    public void logout() {
        User currentUser = UserManager.getInstance().getCurrentUser();
        currentUser = null;
        System.out.println("로그아웃 되었습니다.");
    }
}
