package core.idata;

import core.Account;
import core.DataViewStyle;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Ahmed Ali on 16/02/2016.
 */
public interface IAccountData {
    List<Account> getAccounts() throws WSConnException, NoDataException;
    boolean addAccount(double oldBalance, LocalDate date) throws WSConnException, NoDataException;
}
