package core.igui;

import core.User;
import core.exceptions.LoginException;
import core.exceptions.WSConnException;

public interface IUserControl {
    boolean login(String userName, String password) throws LoginException, WSConnException;
    User getCurrentUser();
}
