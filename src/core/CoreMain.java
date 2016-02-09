package core;

import core.idata.IIncomesData;
import core.idata.IOutgoingsData;
import core.idata.IUserData;
import core.idata.IWithdrawalsData;
import datalayer.IncomesData;
import datalayer.OutgoingsData;
import datalayer.UserData;
import datalayer.WithdrawalsData;

public abstract class CoreMain {
    protected static IUserData userData = new UserData();
    protected static IOutgoingsData outgoingsData = new OutgoingsData();
    protected static IIncomesData incomesData = new IncomesData();
    protected static IWithdrawalsData withdrawalsData = new WithdrawalsData();
}
