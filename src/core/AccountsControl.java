package core;

import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import core.igui.IAccountControl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ahmed Ali on 16/02/2016.
 */
public class AccountsControl extends CoreMain implements IAccountControl {

    private List<Account> accounts;
    private List<LocalDate> dates;
    private HashMap<Integer, List<Integer>> availableDates;

    //cash the last statistics to get balance
    private double yearTotalSales;
    private double yearTotalIncomes;
    private double yearTotalOutgoings;
    private double yearTotalWithdrawals;
    private double yearTotalPurchases;
    private double monthTotalSales;
    private double monthTotalIncomes;
    private double monthTotalOutgoings;
    private double monthTotalWithdrawals;
    private double monthTotalPurchases;

    public AccountsControl(){
        accounts = new ArrayList<>();
        dates = new ArrayList<>();
        availableDates = new HashMap<>();
    }

    @Override   // get accounts by months or year or all
    public List<Account> getAccounts(int year) throws WSConnException, NoDataException {
        accounts.clear();
        accounts.addAll(accountData.getAccounts(year));
        return accounts;
    }

    private void getDates(){
        dates.clear();
        availableDates.clear();

        try {
            dates = accountData.getDates();
        }catch (Exception ex){}

        dates.stream().filter(date -> availableDates.containsKey(
                date.getYear()) == false).map(
                date -> availableDates.put(date.getYear(), new ArrayList<>())
        ).collect(Collectors.toList());

        dates.stream().map(
                date -> availableDates.get(date.getYear()).add(date.getMonthValue())
        ).collect(Collectors.toList());
    }

    @Override
    public List<Integer> getAvailableYears(UpdateType updateType){
        if(updateType == UpdateType.FORCE_UPDATE){
            getDates();
        }
        else if(updateType == UpdateType.UPDATE_IF_NO_DATA){
            if(availableDates.size() == 0)
                getDates();
        }

        List<Integer> years = new ArrayList<>();
        availableDates.keySet().stream().map(
                yr -> years.add(yr)
        ).collect(Collectors.toList());
        return years;
    }

    @Override
    public List<Integer> getAvailableMonths(Integer year){
        return availableDates.get(year);
    }

    @Override
    public Double getMonthTotalSales(int month){ //view accounts with specific month
        monthTotalSales = accounts.stream().filter(
                acc -> acc.getDate().getMonthValue() == month)
                .mapToDouble(Account::getTotalSales).sum();

        return monthTotalSales;
    }

    public Double getMonthTotalIncomes(int month){
        monthTotalIncomes =  accounts.stream().filter(
                acc -> acc.getDate().getMonthValue() == month)
                .mapToDouble(Account::getTotalIncomes).sum();

        return monthTotalIncomes;
    }

    public Double getMonthTotalOutgoings(int month){
        monthTotalOutgoings = accounts.stream().filter(
                acc -> acc.getDate().getMonthValue() == month)
                .mapToDouble(Account::getTotalOutgoings).sum();

        return monthTotalOutgoings;
    }

    public Double getMonthTotalWithdrawals(int month){
        monthTotalWithdrawals = accounts.stream().filter(
                acc -> acc.getDate().getMonthValue() == month)
                .mapToDouble(Account::getTotalwithdrawals).sum();

        return monthTotalWithdrawals;
    }

    public Double getMonthTotalPurchases(int month){
        monthTotalPurchases = accounts.stream().filter(
                acc -> acc.getDate().getMonthValue() == month)
                .mapToDouble(Account::getTotalPurchases).sum();

        return monthTotalPurchases;
    }

    public Double getMonthTotalBalance(){
        return ((monthTotalSales + monthTotalIncomes) - (monthTotalOutgoings + monthTotalWithdrawals));
    }

    @Override
    public Double getYearTotalSales(){  //view all accounts
        yearTotalSales = accounts.stream().mapToDouble(Account::getTotalSales).sum();
        return yearTotalSales;
    }

    public Double getYearTotalIncomes(){
        yearTotalIncomes = accounts.stream().mapToDouble(Account::getTotalIncomes).sum();
        return yearTotalIncomes;
    }

    public Double getYearTotalOutgoings(){
        yearTotalOutgoings = accounts.stream().mapToDouble(Account::getTotalOutgoings).sum();
        return yearTotalOutgoings;
    }

    public Double getYearTotalWithdrawals(){
        yearTotalWithdrawals = accounts.stream().mapToDouble(Account::getTotalwithdrawals).sum();
        return yearTotalWithdrawals;
    }

    public Double getYearTotalPurchases(){
        yearTotalPurchases = accounts.stream().mapToDouble(Account::getTotalPurchases).sum();
        return yearTotalPurchases;
    }

    public Double getYearTotalBalance(){
        return ((yearTotalSales + yearTotalIncomes) - (yearTotalOutgoings + yearTotalWithdrawals));
    }


}
