package core.idata;

import core.DataViewStyle;
import core.Withdraw;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Ahmed ali on 09/02/2016.
 */
public interface IWithdrawalsData {
    List<Withdraw> getWithdrawals(LocalDate date, DataViewStyle style) throws WSConnException, NoDataException;
    boolean addNewWithdrawals(List<Withdraw> withdrawals)throws WSConnException, NoDataException;
}
