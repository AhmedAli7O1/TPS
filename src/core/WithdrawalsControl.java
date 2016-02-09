package core;

import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import core.igui.IWithdrawalsControl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed ali on 09/02/2016.
 */
public class WithdrawalsControl extends CoreMain implements IWithdrawalsControl {
    List<Withdraw> withdrawals;

    public WithdrawalsControl(){
        withdrawals = new ArrayList<>();
    }

    @Override
    public List<Withdraw> getWithdrawals(LocalDate date, DataViewStyle style) throws WSConnException, NoDataException {
        withdrawals.clear();
        withdrawals.addAll(withdrawalsData.getWithdrawals(date, style));
        return this.withdrawals;
    }
}
