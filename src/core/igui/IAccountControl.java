package core.igui;

import core.Account;
import core.DataViewStyle;
import core.UpdateType;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Ahmed Ali on 16/02/2016.
 */
public interface IAccountControl {
    List<Account> getAccounts(int year)  throws WSConnException, NoDataException;
    List<Integer> getAvailableYears(UpdateType updateType);
    List<Integer> getAvailableMonths(Integer year);
    Double getMonthTotalSales(int month);
    Double getYearTotalSales();
}
