package core;

import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import core.igui.IIncomesControl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IncomesControl extends CoreMain implements IIncomesControl {
    List<Income> incomes;

    public IncomesControl(){
        incomes = new ArrayList<>();
    }

    @Override
    public List<Income> getIncomes(LocalDate date, DataViewStyle style) throws WSConnException, NoDataException {
        incomes.clear();
        incomes.addAll(incomesData.getIncomes(date, style));
        return this.incomes;
    }
}
