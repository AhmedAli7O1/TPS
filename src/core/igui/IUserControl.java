package core.igui;

import core.User;
import core.exceptions.LoginException;

public interface IUserControl {
    boolean login(String userName, String password) throws LoginException;
    User getCurrentUser();
}
