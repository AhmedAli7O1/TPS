package core;

import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import core.igui.IWithdrawalsControl;
import gui.GuiMain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed ali on 09/02/2016.
 */
public class WithdrawalsControl extends CoreMain implements IWithdrawalsControl {
    List<Withdraw> withdrawals;
    List<Withdraw> newWithdrawals;

    public WithdrawalsControl(){
        withdrawals = new ArrayList<>();
        newWithdrawals = new ArrayList<>();
    }

    @Override
    public List<Withdraw> getWithdrawals(LocalDate date, DataViewStyle style) throws WSConnException, NoDataException {
        withdrawals.clear();
        withdrawals.addAll(withdrawalsData.getWithdrawals(date, style));
        return this.withdrawals;
    }

    @Override
    public void addWithdrawals(List<Withdraw> newWithdrawals){
        this.newWithdrawals.addAll(newWithdrawals);
    }

    @Override
    public boolean saveChanges() throws WSConnException, NoDataException {
        if(newWithdrawals.size() < 1)
            return true;

        if(withdrawalsData.addNewWithdrawals(newWithdrawals)){
            newWithdrawals.clear();
            return true;
        }
        else return false;
    }
}
