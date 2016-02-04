package core;

import core.exceptions.LoginException;
import core.igui.IUserControl;
import java.util.List;

public class UserControl extends CoreMain implements IUserControl {

    /**
     * extends -> CoreMain Class which is an abstract class
     * contains all instance of classes which implements
     * all interfaces in the Data Layer
     * | object -> userData | class -> UserData | implements -> IUserData |
     */

    private User currentUser;

    @Override // login to app using username and password
    public boolean login(String userName, String password) throws LoginException{
        try {
            currentUser = userData.login(userName, password);
            return true;
        }
        catch (LoginException ex){
            throw new LoginException(ex.getExType());
        }
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }
}
