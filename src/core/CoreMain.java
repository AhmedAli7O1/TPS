package core;

import core.idata.IOutgoingsData;
import core.idata.IUserData;
import datalayer.OutgoingsData;
import datalayer.UserData;

public abstract class CoreMain {
    protected static IUserData userData = new UserData();
    protected static IOutgoingsData outgoingsData = new OutgoingsData();
}
