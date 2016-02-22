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

    public AccountsControl(){
        accounts = new ArrayList<>();
        dates = new ArrayList<>();
        availableDates = new HashMap<>();
    }

    @Override   // get accounts by months or year or all
    public List<Account> getAccounts(UpdateType updateType) throws WSConnException, NoDataException {
        if(updateType == UpdateType.FORCE_UPDATE){
            accounts.clear();
            accounts.addAll(accountData.getAccounts());
            return accounts;
        }
        else {
            if(accounts.size() == 0) {
                accounts.clear();
                accounts.addAll(accountData.getAccounts());
            }

            return accounts;
        }
    }

    private void getDates(){
        try {
            getAccounts(UpdateType.UPDATE_IF_NO_DATA);
        }catch (Exception ex){ ex.printStackTrace(); }

        dates.clear();
        availableDates.clear();

        accounts.stream().map(acc -> dates.add(acc.getDate())).collect(Collectors.toList());

        dates.stream().filter(date -> availableDates.containsKey(
                date.getYear()) == false).map(
                date -> availableDates.put(date.getYear(), new ArrayList<>())
        ).collect(Collectors.toList());

        dates.stream().map(
                date -> availableDates.get(date.getYear()).add(date.getMonthValue())
        ).collect(Collectors.toList());
    }

    @Override
    public List<Integer> getAvailableYears(){
        getDates();
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
    public Double getMonthTotalSales(int month, int year){ //view accounts with specific month
        return accounts.stream().filter(
                acc -> acc.getDate().getMonthValue() == month &&
                        acc.getDate().getYear() == year)
                .mapToDouble(Account::getSales).sum();
    }

    @Override
    public Double getMonthTotalIncomes(int month, int year){
        return   accounts.stream().filter(
                acc -> acc.getDate().getMonthValue() == month &&
                        acc.getDate().getYear() == year)
                .mapToDouble(Account::getIncomes).sum();
    }

    @Override
    public Double getMonthTotalOutgoings(int month, int year){
        return accounts.stream().filter(
                acc -> acc.getDate().getMonthValue() == month &&
                        acc.getDate().getYear() == year)
                .mapToDouble(Account::getOutgoings).sum();
    }

    @Override
    public Double getMonthTotalWithdrawals(int month, int year){
        return accounts.stream().filter(
                acc -> acc.getDate().getMonthValue() == month &&
                        acc.getDate().getYear() == year)
                .mapToDouble(Account::getWithdrawals).sum();
    }

    @Override
    public Double getMonthTotalPurchases(int month, int year){
        return accounts.stream().filter(
                acc -> acc.getDate().getMonthValue() == month &&
                        acc.getDate().getYear() == year)
                .mapToDouble(Account::getPurchases).sum();
    }

    @Override
    public Double getMonthOldBalance(int month, int year){
        return accounts.stream().filter(
                acc -> acc.getDate().getMonthValue() == month &&
                        acc.getDate().getYear() == year)
                .mapToDouble(Account::getOldBalance).sum();
    }

    @Override
    public Double getMonthBalance(int month, int year){
        return accounts.stream().filter(
                acc -> acc.getDate().getMonthValue() == month &&
                        acc.getDate().getYear() == year)
                .mapToDouble(Account::getBalance).sum();
    }

    @Override
    public Double getMonthTotalDebts(int month, int year){
        return accounts.stream().filter(
                acc -> acc.getDate().getMonthValue() == month &&
                        acc.getDate().getYear() == year
        ).findFirst().get().getDebts();
    }

    @Override
    public Double getMonthProfits(int month, int year){
        return accounts.stream().filter(
                acc -> acc.getDate().getMonthValue() == month &&
                        acc.getDate().getYear() == year
        ).findFirst().get().getProfits();
    }

    @Override
    public Double getYearTotalSales(){  //view all accounts
        return accounts.stream().mapToDouble(Account::getSales).sum();
    }

    @Override
    public Double getYearTotalIncomes(){
        return accounts.stream().mapToDouble(Account::getIncomes).sum();
    }

    @Override
    public Double getYearTotalOutgoings(){
        return accounts.stream().mapToDouble(Account::getOutgoings).sum();
    }

    @Override
    public Double getYearTotalWithdrawals(){
        return accounts.stream().mapToDouble(Account::getWithdrawals).sum();
    }

    @Override
    public Double getYearTotalPurchases(){
        return accounts.stream().mapToDouble(Account::getPurchases).sum();
    }

    @Override
    public Double getYearTotalProfits(){
        return accounts.stream().mapToDouble(Account::getProfits).sum();
    }

    @Override
    public int getAccountId(LocalDate date){
        return accounts.stream().filter(
                acc -> acc.getDate().isEqual(date.withDayOfMonth(1))).findFirst().get().getId();
    }
}
