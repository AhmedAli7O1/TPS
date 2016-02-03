package core.igui;

import core.User;

public interface IUserControl {
    boolean login(String userName, String password);
    int getUserCount();
    User getCurrentUser();
}
