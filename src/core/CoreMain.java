package core;

import core.idata.IUserData;
import datalayer.UserData;

public abstract class CoreMain {
    protected static IUserData userData = new UserData();
}
