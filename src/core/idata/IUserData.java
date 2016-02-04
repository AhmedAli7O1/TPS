package core.idata;

import core.User;
import core.exceptions.LoginException;

import java.util.List;

public interface IUserData {
    User login(String uName, String uPass) throws LoginException;
}
