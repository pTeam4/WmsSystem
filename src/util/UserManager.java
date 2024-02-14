package util;

import vo.User;

public class UserManager {
    private static UserManager instance;
    private User currentUser;

    private UserManager() {
        // private 생성자로 외부에서 인스턴스를 생성하는 것을 막음
    }

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
