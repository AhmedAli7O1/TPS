package core;

import core.idata.*;
import datalayer.*;

public abstract class CoreMain {
    protected static IUserData userData = new UserData();
    protected static IOutgoingsData outgoingsData = new OutgoingsData();
    protected static IIncomesData incomesData = new IncomesData();
    protected static IWithdrawalsData withdrawalsData = new WithdrawalsData();
    protected static IPurchasesData purchasesData = new PurchasesData();
    protected static IAccountData accountData = new AccountsData();
}
