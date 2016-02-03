package core;

import core.igui.IUserControl;

import java.util.ArrayList;
import java.util.List;

public class UserControl extends CoreMain implements IUserControl {

    /**
     * extends -> CoreMain Class which is an abstract class
     * contains all instance of classes which implements
     * all interfaces in the Data Layer
     * | object -> userData | class -> UserData | implements -> IUserData |
     */

    private List<User> users;
    private User currentUser;

    public UserControl(){
        // load all user data
        users = new ArrayList<>(userData.getAllUsers());
    }

    @Override // login to app using username and password
    public boolean login(String userName, String password){
        for(User ur : users){
            if(userName.equals(ur.getName()) && password.equals(ur.getPassword())) {
                currentUser = ur;
                return true;
            }
        }
        return false;
    }

    @Override
    public int getUserCount() {
        return users.size();
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }
}
