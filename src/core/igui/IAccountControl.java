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
    List<Account> getAccounts(UpdateType updateType) throws WSConnException, NoDataException;
    List<Integer> getAvailableYears();
    List<Integer> getAvailableMonths(Integer year);

    Double getMonthTotalSales(int month, int year);
    Double getMonthTotalIncomes(int month, int year);
    Double getMonthTotalOutgoings(int month, int year);
    Double getMonthTotalWithdrawals(int month, int year);
    Double getMonthTotalPurchases(int month, int year);
    Double getMonthOldBalance(int month, int year);
    Double getMonthBalance(int month, int year);
    Double getMonthTotalDebts(int month, int year);
    Double getMonthProfits(int month, int year);

    Double getYearTotalSales();
    Double getYearTotalIncomes();
    Double getYearTotalOutgoings();
    Double getYearTotalWithdrawals();
    Double getYearTotalPurchases();

    int getAccountId(LocalDate date);
}
