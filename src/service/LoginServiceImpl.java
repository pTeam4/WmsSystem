package service;

import config.GetTexts;
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
    }

    @Override
    public void logout() {
        User.getInstance().logout();
        System.out.println("로그아웃 되었습니다.");
    }
}
